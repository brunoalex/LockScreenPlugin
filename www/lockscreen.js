// Require the cordova/exec module
var exec = require('cordova/exec');

var LockScreenPlugin = {
    lock: function (success, error) {
        // Use exec to communicate with the native layer
        exec(success, error, 'LockScreenPlugin', 'lock', []);
    }
};

module.exports = LockScreenPlugin;
