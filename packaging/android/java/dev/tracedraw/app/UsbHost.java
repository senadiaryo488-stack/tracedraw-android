package dev.tracedraw.app;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Small USB-host discovery/permission layer for printer backends.
 *
 * This deliberately does not send print data: Epson ESC/P-R and HP P1102
 * need printer-specific rendering/transport implementations. Device class
 * is not filtered because many USB printers expose vendor-specific classes.
 */
public final class UsbHost {
    public interface PermissionCallback {
        void onResult(UsbDevice device, boolean granted);
    }

    private static final String ACTION_USB_PERMISSION =
        "dev.tracedraw.app.USB_PERMISSION";
    private final Context context;
    private final UsbManager manager;

    public UsbHost(Context context) {
        this.context = context.getApplicationContext();
        this.manager = (UsbManager) this.context.getSystemService(Context.USB_SERVICE);
    }

    /** One tab-separated row per attached USB device. */
    public String listDevices() {
        if (manager == null) return "";
        HashMap<String, UsbDevice> devices = manager.getDeviceList();
        StringBuilder out = new StringBuilder();
        for (UsbDevice device : devices.values()) {
            if (out.length() > 0) out.append('\n');
            out.append(clean(device.getDeviceName())).append('\t')
                .append(device.getVendorId()).append('\t')
                .append(device.getProductId()).append('\t')
                .append(device.getDeviceClass()).append('\t')
                .append(device.getInterfaceCount()).append('\t')
                .append(manager.hasPermission(device) ? "1" : "0");
        }
        return out.toString();
    }

    public boolean hasPermission(UsbDevice device) {
        return manager != null && device != null && manager.hasPermission(device);
    }

    /** Request Android's per-device USB permission; callback runs on main thread. */
    public void requestPermission(final UsbDevice device, final PermissionCallback callback) {
        if (manager == null || device == null || callback == null) {
            if (callback != null) callback.onResult(device, false);
            return;
        }
        if (manager.hasPermission(device)) {
            callback.onResult(device, true);
            return;
        }

        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive(Context receiverContext, Intent intent) {
                if (!ACTION_USB_PERMISSION.equals(intent.getAction())) return;
                try {
                    context.unregisterReceiver(this);
                } catch (IllegalArgumentException ignored) {
                    // Already unregistered.
                }
                UsbDevice returned = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                boolean granted = intent.getBooleanExtra(
                    UsbManager.EXTRA_PERMISSION_GRANTED, false);
                callback.onResult(returned == null ? device : returned, granted);
            }
        };
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            context.registerReceiver(receiver, filter);
        }
        Intent intent = new Intent(ACTION_USB_PERMISSION)
            .setPackage(context.getPackageName());
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= 31) flags |= PendingIntent.FLAG_MUTABLE;
        PendingIntent permissionIntent = PendingIntent.getBroadcast(
            context, device.getDeviceId(), intent, flags);
        manager.requestPermission(device, permissionIntent);
    }

    /** Open the device after permission has been granted. Caller owns the connection. */
    public UsbDeviceConnection openDevice(UsbDevice device) {
        if (!hasPermission(device)) return null;
        return manager.openDevice(device);
    }

    private static String clean(String value) {
        return value == null ? "" : value.replace('\t', ' ').replace('\n', ' ');
    }
}
