angular.module("troubleshoot", []).controller("HomeController", ["$scope",  function ($scope) {
    var ctl = this; ctl.title = "Hello world";
    ctl.activateScanner = function () {
        var run = AndroidInterface.activateScanner();
        var context = ctl;
        run.then = callback => {
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name);
        }
        
        run.then((a) => {
            $scope.$apply(() => {                
                context.barcodeResponse = a;
            });
        });
    };

    ctl.getDeviceId = function () {
        var run = AndroidInterface.getDeviceId();
        
        run.then = function(callback){
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name);
        }
        
        run.then(function(a){
            $scope.$apply(() => {                
                ctl.deviceId = a;
            });
        });
    };

    ctl.getApplicationVersion = function () {
        var run = AndroidInterface.getApplicationVersion();
        
        run.then = function(callback){
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name);
        }
        
        run.then(function(a){
            $scope.$apply(() => {                
                ctl.applicationVersion = a;
            });
        });
    };

}]);

