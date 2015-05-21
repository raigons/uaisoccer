define([
	"knockout"
], function(ko)  {
	"use strict";

	var GreetingViewModel = function() {
		var that = {};
		that.name = ko.observable(name);

		that.save = function(name) {
			console.log(arguments);
		};

		return that;
	};

	return GreetingViewModel;

});