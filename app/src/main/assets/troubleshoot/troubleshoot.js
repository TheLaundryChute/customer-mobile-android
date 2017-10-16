angular.module("troubleshoot", []).controller("HomeController", ["$scope",  function ($scope) {
    var ctl = this; ctl.title = "Hello world";
    ctl.activateScanner = function () {
        var run = AndroidInterface.activateScanner();
        var context = ctl;
        run.then = callback => {
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name, true);
        }
        
        run.then((a) => {
            $scope.$apply(() => {                
                context.barcodeResponse = a;
            });
        });
    };

    ctl.authenticate = function () {
        var run = AndroidInterface.authenticate('dmartin@pitt.edu', 'hell yea dude', true);
        
        run.then = function(callback){
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name);
        }
        
        run.then(function(a){
            $scope.$apply(() => {                
                ctl.setCredResponse = a;
            });
        });
    };
    ctl.getCredentials = function () {
        var run = AndroidInterface.getCredentials();
        
        run.then = function(callback){
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name);
        }
        
        run.then(function(a){
            $scope.$apply(() => {                
                ctl.getCredResponse = a;
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

    ctl.getDeviceInfo = function () {
        var run = AndroidInterface.getDeviceInfo();
        
        run.then = function(callback){
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name);
        }
        
        run.then(function(a){
            $scope.$apply(() => {                
                ctl.deviceInfo = a;
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

    ctl.refreshApplication = function () {
        var run = AndroidInterface.refreshApplication();
        
        run.then = function(callback){
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name);
        }
        
        run.then(function(a){
            $scope.$apply(() => {                
                ctl.refreshApplicationResponse = a;
            });
        });
    };

}]);

