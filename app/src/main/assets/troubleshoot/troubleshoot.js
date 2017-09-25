angular.module("troubleshoot", []).controller("HomeController", ["$http", function ($http) {
    var ctl = this; ctl.title = "Hello world";
    ctl.doBarcode = function () {
        var run = AndroidInterface.activateScanner();
        
        run.then = function(callback){
            var name = "callback_"+Math.floor((Math.random() * 100000));
            window[name] = callback
            return run.thenJS(name);
        }
        
        run.then(function(a){
            alert("CALLBACK in JAVASCRIPT "+JSON.stringify(a));
        });
    };

    ctl.doLock = function () {
        $http.post("http://tlc-locker/lock", ctl.lockRequest)
            .then(function (r) { ctl.responseLock = r.data })
            .catch(function (r) { ctl.responseLock = r });
    };

    ctl.doStatus = function () {
        $http.post("http://tlc-locker/status", ctl.statusRequest)
            .then(function (r) { ctl.responseStatus = r.data })
            .catch(function (r) { ctl.responseStatus = r });
    };

    ctl.doAll = function () {
        $http.get("http://tlc-locker/all")
            .then(function (r) { ctl.responseAll = r.data })
            .catch(function (r) { ctl.responseAll = r });
    };

    ctl.doReset = function () {
        alert('is this working?');
        var response = AndroidInterface.dropOffScan('jcash@pitt.edu');
        // $http.get("http://tlc-locker/reset")
        //     .then(function (r) { ctl.responseReset = r.data })
        //     .catch(function (r) { ctl.responseReset = r });
    };


    ctl.doSettings = function () {
        $http.get("http://tlc-locker/settings")
            .then(function (r) { ctl.responseSettings = r.data })
            .catch(function (r) { ctl.responseSettings = r });
    };
    ctl.doInfo = function () {
        $http.get("http://tlc-locker/info")
            .then(function (r) { ctl.responseInfo = r.data })
            .catch(function (r) { ctl.responseInfo = r });
    };
    ctl.doReload = function () {
        $http.get("http://tlc-locker/reload")
            .then(function (r) { ctl.responseInfo = r.data })
            .catch(function (r) { ctl.responseInfo = r });
    };
}]);

