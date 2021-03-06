
/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */
var vaccine = angular.module('VaccineModule', ['openlmis', 'ngTable','ui.bootstrap','nsPopover','ngTable',"datatables"]).config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        // this route is responding to
        .when('/internation_arrival', {
            templateUrl: "/public/pages/stock/partials/international_shipment.html",
            controller: "StockModuleController"
        })
        // this route is responding topackage arrival route
        .when('/package_arrivals', {
            templateUrl: "/public/pages/stock/partials/package_arrival.html",
            controller: "StockModuleController"
        })
        // this route is responding to items route
        .when('/items', {
            templateUrl: "/public/pages/stock/partials/stock_items.html",
            controller: "StockModuleController"
        })
        // this route is responding to prepare  route
        .when('/prepare', {
            templateUrl: "/public/pages/stock/partials/prepare.html",
            controller: "StockModuleController"
        })
        // this route is responding to sending package
        .when('/sending_package', {
            templateUrl: "/public/pages/stock/partials/sending_package.html",
            controller: "StockModuleController"
        })
        // this route is responding to confirm  route
        .when('/confirm', {
            templateUrl: "/public/pages/stock/partials/confirm_reception.html",
            controller: "StockModuleController"
        })
        // this route is responding to receive route
        .when('/receive', {
            templateUrl: "/public/pages/stock/partials/receive.html",
            controller: "StockReceiveController"
        })
        // this route is responding to home route
        .when('/home', {
            templateUrl: "/public/pages/stock/partials/stockHome.html",
            controller: "StockModuleController"
        })
       // this route is responding to pre advice route
        .when('/preadvice', {
            templateUrl: "/public/pages/stock/partials/preadvice.html",
            controller: "StockModuleController"
        })
        // this route is responding to pre advice route
        .when('/package_information', {
            templateUrl: "/public/pages/stock/partials/package_information.html",
            controller: "StockModuleController"
        })
        // this route is responding to scan package route
        .when('/', {
            templateUrl: "/public/pages/stock/partials/receive.html",
            controller: "StockReceiveController"
        })
        // this route is responding to  any link other than registered
        .otherwise({redirectTo: '/'});

}]).directive('onKeyup', function () {
        return function (scope, elm, attrs) {
            elm.bind("keyup", function () {
                scope.$apply(attrs.onKeyup);
            });
        };
});

vaccine.controller("StockModuleController",function($scope,$http,$location,$routeParams,$resource,$filter){

    $scope.packagesJson = null;
    $scope.data = {};
$scope.regions = null;
$scope.packageStructure = {
        "delivery_status": null,
        "expire_date":null,
        "lot_number":null,
        "manufacture_date": null,
        "number_of_doses":null,
        "purchasing_order_number":null,
        "shipment_id":null ,
        "vaccine_packaging": null,
        "vaccine_packaging_id":null
    }
    initialize();
    function initialize() {
        var sscc_input =angular.element('#sscc_number_field');
        $(sscc_input).focus();
        $scope.editFlag = false;
        $scope.productSelectOption = {maximumSelectionSize : 4};
        $scope.$parent.currentTab = 'SUMMARY';
        $scope.showProductsFilter = true;

    }

    //getting var reports
    $http.get('/stock/var/details').
        success(function(data) {
            $scope.data.reports = data.var_details;
        }).
        error(function(data) {
            console.log("Error:" + data);
        });

    // temporary api call to pull packages
    //$http.get('/public/pages/stock/dummData/manufacturerpackage.json').
    $scope.fecthPackages = function(){
        $http.get('/stock/manufacture/package').
            success(function(data, status, headers, config) {
                $scope.packagesJson = data.manufacture_packages;
            }).
            error(function(data) {
                console.log("Error:" + data);
            });
    }
    $scope.fecthPackages();

    // load all vaccines
    $http.get('/stock/vaccine').
        success(function(data, status, headers, config) {
            $scope.vaccines = data;
        }).
        error(function(data) {
            console.log("Error:" + data);
        });

    $scope.pullPackaging = function(vaccine_id){
        var pullUrl =  encodeURI("/stock/vaccine/packaging?filter=vaccine_id:eq:"+vaccine_id);
        $http.get(pullUrl).
            success(function(data, status, headers, config) {
                $scope.packaging = data;
                $scope.gtinSelect = true;
               console.log(data);
            }).
            error(function(data) {
                console.log("Error:" + data);
            });

    }
    // HANDLING PRE ADVICE MENU
    $scope.tabToggle = function(menu){
        $scope.container = menu;
        if(menu=="all"){
            $scope.filtering = false;
            $scope.all = true;
            $scope.pending = false;
            $scope.received = false;
            $scope.viewForm = false;
            $scope.viewTable = true;
            $scope.editForm = false;
        }
        if(menu=="received"){
            $scope.filtering =true;
            $scope.status = "received";
            $scope.all = false;
            $scope.pending = false;
            $scope.received = true;
            $scope.viewForm = false;
            $scope.viewTable = true;
            $scope.editForm = false;
        }
        if(menu=="pending"){
            $scope.filtering =true;
            $scope.all = false;
            $scope.status = "pending";
            $scope.pending = true;
            $scope.received = false;
            $scope.viewForm = false;
            $scope.viewTable = true;
            $scope.editForm = false;
        }
    }
    $scope.tabToggle('all');

    // table between add form and table
    //$scope.viewTable = true;
    var viewTable   = angular.element("#viewTable");
    var editForm    = angular.element("#editForm");
    var viewForm    = angular.element("#viewForm");
    jQuery(viewTable).show();
    jQuery(editForm).hide();
    jQuery(viewForm).hide();
    $scope.toggleFormAndTable = function(tobeSeen){
        if(tobeSeen=="addNew"){
            //$scope.viewForm = true;
            //$scope.viewTable = false;
            //$scope.editForm = false;
            jQuery(viewTable).hide();
            jQuery(editForm).hide();
            jQuery(viewForm).show();
        }
        if(tobeSeen=="cancelAdd"){
            //$scope.viewForm = false;
            //$scope.viewTable = true;
            //$scope.editForm = false;
            jQuery(viewTable).show();
            jQuery(editForm).hide();
            jQuery(viewForm).hide();
        }
    }

    // adding package
    //$scope.package = null;
    $scope.addPackage = function(data){

            var newObject = $scope.packageStructure;
            newObject.delivery_status= 'received';
            newObject.expire_date= data.expire_date;
            newObject.lot_number= data.lot_number;
            newObject.manufacture_date= data.manufacture_date;
            newObject.number_of_doses= data.number_of_doses;
            newObject.purchasing_order_number= "order";
            newObject.shipment_id= data.shipment_id;
            newObject.vaccine_packaging= null;
            newObject.vaccine_packaging_id= data.vaccine_packaging_id;

            $http.post('/stock/manufacture/package',newObject).
                success(function(data, status, headers, config) {
                    jQuery(viewTable).show();
                    jQuery(viewForm).hide();
                    //$scope.viewForm = false;
                    //$scope.viewTable = true;
                    $scope.fecthPackages();
                }).
                error(function(data) {
                    console.log("Error:" + data);
                });
        }

    $scope.editPackage = function(id,packageObject){
        console.log(id);
        $scope.editPackage = {};
        //$scope.viewForm = false;
        //$scope.editForm = true;
        //$scope.viewTable = false;
        jQuery(viewTable).hide();
        jQuery(editForm).show();
        jQuery(viewForm).hide();
        $scope.package = packageObject;

        $scope.editPackage.delivery_status= 'pending';
        //$scope.editPackage.expire_date= formatDate(packageObject.expire_date);
        $scope.editPackage.lot_number= packageObject.lot_number;
        //$scope.editPackage.manufacture_date= Date(formatDate(packageObject.manufacture_date));
        $scope.editPackage.number_of_doses= packageObject.number_of_doses;
        $scope.editPackage.purchasing_order_number= "order";
        $scope.editPackage.shipment_id= packageObject.shipment_id;
        $scope.editPackage.vaccine_packaging= null;
        $scope.editPackage.vaccine_packaging_id= packageObject.vaccine_packaging_id;

        $scope.fecthPackages();

    }
    $scope.updatePackage = function(id,packageObject){
        packageObject.id=id;
        console.log(packageObject);
        var updateUrl =  encodeURI('/stock/manufacture/package/');
        $http.post(updateUrl,packageObject).
            success(function(data, status, headers, config){
                $scope.fecthPackages();
                //$scope.viewForm = false;
                //$scope.editForm = false;
                //$scope.viewTable = true;
                jQuery(viewTable).show();
                jQuery(editForm).hide();
                jQuery(viewForm).hide();

            }).
            error(function(data) {
                console.log("Error:" + data);
            });


    }
    $scope.cancelEditpackage = function(editPackage){
        //$scope.editPackage = null;
        //$scope.viewForm = false;
        //$scope.editForm = false;
        //$scope.viewTable = true;
        jQuery(viewTable).show();
        jQuery(editForm).hide();
        jQuery(viewForm).hide();
    }
    $scope.deletePackage = function(id,packageObject){
        var updateUrl =  encodeURI('/stock/manufacture/package/'+id);
        //Calling Web API to fetch shopping cart items
        $http.delete(updateUrl).success(function(data){
            //Passing data to deferred's resolve function on successful completion
            $scope.fecthPackages();
        }).error(function(){

            //Sending a friendly error message in case of failure
            deferred.reject("An error occured while editing item");
        });
    }
    // cancell package adding
    $scope.cancelAddPackage = function(data){
        $scope.package = null;
        $scope.viewForm = false;
        $scope.editForm = false;
        $scope.viewTable = true;
        jQuery(viewTable).show();
        jQuery(editForm).hide();
        jQuery(viewForm).hide();
    }
    // action to scan package

    //// RECEIVE PACKAGE
    $scope.scan_afresh = true;
    $scope.scan_lotnumber = false;
     // action to scan package
    var scan_package_button =angular.element('#scan_package_button');
    $scope.scapPackage = function(){
        var shipping_number =angular.element('#shipping_number').val();
        $scope.filtering_shipping_number = shipping_number;
        //window.location.href = link;// normal angular function don work
        $scope.scan_lotnumber = true;
        $scope.scan_afresh = false;
    };
    // action to scan sub package
    $scope.scapLotNumber = function(){
      var lot_number =angular.element('#lot_number_filed').val();
        $scope.lot_number = lot_number;
      var link = encodeURI("/public/pages/stock/index.html#/"+"confirm?lotn="+lot_number+"&ssc="+$scope.sscc_number);
      window.location.href = link;// normal angular function don work
    }

    var confirm_package_button = angular.element("#confirm_package_button");
    $scope.condition = {'quantity':null,'physical_damage':null,'vvmstatus':null,'temp_monitors':null,'problems':null};
    $(confirm_package_button).bind("click",function(){
        var packageDelt = null;
        angular.forEach($scope.packagesJson,function(value,index){
            if(value.lot_number==$scope.lot_number){
                packageDelt = value;
            }
        });

         var confirmObject = {
            package_number: packageDelt.shipment_id,
            lot_number: packageDelt.lot_number,
            number_as_expected:$scope.condition.quantity,
            gtin: 'hyr23',
            number_recieved: 3,
            number_expected: 21,
            physical_damage: $scope.condition.physical_damage,
            vvm_status: $scope.condition.vvmstatus,
            problems: $scope.condition.problems,
            receiving_user:1,
            user:1,
            vaccine_packaging_id:2
        }

        $http({
            method: 'POST',
            url: '/stock/package/arrival',
            data: JSON.stringify(confirmObject),
            headers: {'Content-Type': 'application/json'},
            success: function(obj) {
               console.log(obj);
            },
            error:function(data) {
                        console.log("Error:" + data);
                    }
        });
    });
    $scope.cancelConfirmation = function(){

        //var link = encodeURI("/public/pages/stock/index.html#/receive");
        //window.location.href = link;// normal angular function don work
    }

    $scope.numberOfBoxes = function(doses_per_vials,vials_per_box,number_of_doses){
        $scope.boxes = parseInt(number_of_doses/(doses_per_vials*vials_per_box));
        var boxes = $scope.boxes;
        return boxes;
    }
    if($routeParams.ssc){
        $scope.sscc_number = $routeParams.ssc;
    }
    if($routeParams.lotn){
    $scope.lot_number = $routeParams.lotn;
    }

    var regions = function(){
        ///stock/partials/localstore/regions.json
        $http.get('../../js/stock/module/localstore/regions.json').
            success(function(data, status, headers, config) {
                $scope.regions = data;
            }).
            error(function(data) {
                console.log("Error:" + data);
            });
    }
    regions();

    // prepare package and send to selected destination
    $scope.lotScanner =false;
    $scope.sannNotify =false;
    $scope.sannedtable =false;
    $scope.displayErrorAtLot =false;
    $scope.sendPackage = function(destination){
        $scope.lotScanner = true;
        $scope.sannNotify =false;
        $scope.sannedtable =false;
        $scope.scanned_lot_number = null;

        $scope.scanItem = function(){
            if($scope.scanned_lot_number){
                $scope.displayErrorAtLot =false;
                if($scope.checkExpiredate()){
                    $scope.sannNotify=true;
                }else{
                    $scope.sannNotify =false;
                    $scope.sannedtable =true;
                }
            }else{
                $scope.displayErrorAtLot =true;
            }

        }

        $scope.proceedPreparation = function(){
            $scope.sannNotify =false;
            $scope.sannedtable =true;
        }

        $scope.quitPreparation = function(){

            $scope.sannNotify =false;
            $scope.sannedtable =false;
        }

    }

    $scope.addingPackage = function(scannedPackage){
        console.log(scannedPackage);
    }

    $scope.checkExpiredate = function(){
        return true;
    }
    function formatDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }
});


vaccine.controller("StockMenuController",function($scope, $location) {
    var dashboardMenuService = {};

    dashboardMenuService.tabs = [
        {header: 'PreAdvice', content:'/public/pages/stock/index.html#/preadvice', name:'preadvice', closable:false, displayOrder: 0},
        {header: 'ReceivePackage', content:'/public/pages/stock/index.html#/receive', name:'receive', closable:false, displayOrder: 1},
        {header: 'VAR', content:'/public/pages/stock/index.html#/prepare', name:'prepare', closable:false, displayOrder: 2},
        {header: 'StockItems', content:'/public/pages/stock/index.html#/items', name:'items', closable: false, displayOrder: 3},
        {header: 'PackagingInformation', content:'/public/pages/stock/index.html#/package_information', name:'items', closable: false, displayOrder: 4}
    ];


    $scope.dashboardTabs = dashboardMenuService.tabs;

    $scope.$on('dashboardTabUpdated', function(){
    $scope.dashboardTabs = dashboardMenuService.tabs;
    });
    $scope.closeTab = function(tabName){
    dashboardMenuService.closeTab(tabName);
    };

});

vaccine.controller("StockPackageController",function($scope,$http,$location,$routeParams,$resource,$filter) {
    //initialize variables
    $scope.data = {};
    $scope.data.vaccines = {};
    $scope.data.vaccine_packages = {};
    $scope.data.manufactures = {};
    $scope.showTable = true;
    $scope.showEdit = false;

    // load all vaccines
    $http.get('/stock/vaccine').success(function(data, status, headers, config) {
            $scope.data.vaccines = data;
        }).error(function(data) {
            console.log("Error:" + data);
        });


    // load all vaccines packaging information
    $scope.pullPackagingInformation = function(){
        $scope.data.vaccine_packages = {};
        $http.get('/stock/vaccine/packaging'). success(function(data, status, headers, config) {
            $scope.data.vaccine_packages = data.vaccine_packagings;
        }). error(function(data) {
            console.log("Error:" + data);
        });
    }
    $scope.pullPackagingInformation();

    // load all vaccines packaging information
    $http.get('/vaccine/manufacturers').success(function(data, status, headers, config) {
            $scope.data.manufactures = data.manufacturers;
        }).error(function(data) {
            console.log("Error:" + data);
        });

    //display the add table
    $scope.addNew = function(){
        $scope.showTable = false;
        $scope.showEdit = false;
    }

    //display the edit form
    $scope.editPackageInfo = function(id,package){
        $scope.showTable = false;
        $scope.showEdit = true;
        $scope.data.package = package;
        package.vaccine_id = package.vaccine.id;
        package.manufacturer_id = package.manufacturer.id
    }

   //display the add table
    $scope.adding = false;
    $scope.addNewPackage = function(package){
        $scope.adding = true;
        package.status = '';
        package.country_id = 1;
        $http.post('/stock/vaccine/packaging',package). success(function(data) {
            $scope.pullPackagingInformation();
            $scope.cancelAdd();
        }). error(function(data) {
            console.log("Error:" + data);
        });
    }

    //hide the add table
    $scope.cancelAdd = function(){
        $scope.data.package = {};
        $scope.showTable = true;
        $scope.showEdit = false;
    }

    //update package information
    $scope.update = function(package){
        var updateUrl =  '/stock/vaccine/packaging/'+id;
        //Calling Web API to fetch shopping cart items
        $http.post(updateUrl,package).success(function(data){
            //Passing data to deferred's resolve function on successful completion
            $scope.pullPackagingInformation();
            $scope.cancelAdd();
        }).error(function(data){
            console.log("Error:" + data);
            //Sending a friendly error message in case of failure
        });
    }


    //hide the add table
    $scope.deletePackageInfo = function(id,package){
        var updateUrl =  '/stock/vaccine/packaging/'+id;
        //Calling Web API to fetch shopping cart items
        $http.delete(updateUrl).success(function(data){
            //Passing data to deferred's resolve function on successful completion
            $scope.pullPackagingInformation();
        }).error(function(data){
            console.log("Error:" + data);
            //Sending a friendly error message in case of failure
        });
    }
});



services.factory('StockMenuService',function($rootScope,$location){
    var dashboardMenuService = {};

    dashboardMenuService.tabs = [
        {header: 'PreAdvice', content:'/public/pages/stock/index.html#/preadvice', name:'preadvice', closable:false, displayOrder: 0},
        {header: 'ReceivePackage', content:'/public/pages/stock/index.html#/receive', name:'receive', closable:false, displayOrder: 0},
        {header: 'PreparePackage', content:'/public/pages/stock/index.html#/prepare', name:'prepare', closable:false, displayOrder: 1},
        {header: 'StockItems', content:'/public/pages/stock/index.html#/items', name:'items', closable: false, displayOrder: 2}
    ];

    dashboardMenuService.addTab = function(header, content, name, closable, displayOrder){
        var tab = isTabExists(name);
        var newTab = {header:header, content: content, name:name, closable:closable, displayOrder: displayOrder};

        if(_.isEqual(tab, newTab)){
            return;
        }
        if(_.isUndefined(tab)){
            dashboardMenuService.tabs.push(newTab);
        }else if(tab.name === newTab.name){ //replace
            dashboardMenuService.tabs[_.indexOf(dashboardMenuService.tabs,tab)] = newTab;
        }

        broadcastUpdate();
    };

    dashboardMenuService.closeTab = function(tabName){
        var tab = isTabExists(tabName);

        var closedTabIndex = _.indexOf(dashboardMenuService.tabs,tab);

        dashboardMenuService.tabs = _.reject(dashboardMenuService.tabs, function(tab){return tab.name === tabName && tab.closable === true;});

        var tabToShow = '';

        if(!isUndefined(tab)){
            var visibleTab;
            var nextTab = _.findWhere(dashboardMenuService.tabs,{displayOrder: tab.displayOrder + 1});

            if(nextTab !== undefined){
                visibleTab = nextTab.content;
            }else if(closedTabIndex > 0){
                visibleTab = dashboardMenuService.tabs[closedTabIndex-1].content;
            }
            tabToShow += visibleTab.slice(visibleTab.indexOf('#')+1);
        }
        broadcastUpdate();
        $location.path(tabToShow);
    };
    var broadcastUpdate = function(){
        $rootScope.$broadcast('dashboardTabUpdated');
    };
    var isTabExists = function(tabName){
        var tab = _.findWhere(dashboardMenuService.tabs, {name:tabName});
        if(_.isUndefined(tab)){
            return undefined;
        }
        return tab;
    };
    return dashboardMenuService;

});
