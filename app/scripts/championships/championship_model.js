define([
	"knockout"
], function (ko)  {
	"use strict";

	var Championship = function (name)  {
		var that = {};
		that.name = ko.observable(name);

		return that;
	};

	return Championship;

});