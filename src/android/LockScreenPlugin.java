import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;  // Add this line
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.app.admin.DeviceAdminReceiver; // Import the DeviceAdminReceiver class



public class LockScreenPlugin extends CordovaPlugin {

    static final int ACTIVATION_REQUEST = 47; // identifies our request id
    DevicePolicyManager devicePolicyManager;
    ComponentName deviceAdmin;

    @Override
    protected void pluginInitialize() {
        devicePolicyManager = (DevicePolicyManager) cordova.getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        deviceAdmin = new ComponentName(cordova.getActivity(), DeviceAdminReceiver.class); // Use the default DeviceAdminReceiver
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
         Log.d("LockScreenPlugin", "execute method called with action: " + action);
        if ("lock".equals(action)) {
            if (!devicePolicyManager.isAdminActive(deviceAdmin)) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdmin);
                cordova.getActivity().startActivityForResult(intent, ACTIVATION_REQUEST);
                // You should implement onActivityResult to check if the user enabled the Device Admin
            } else {
                devicePolicyManager.lockNow();
                callbackContext.success();
            }
            return true;
        }
        return false;
    }
}
