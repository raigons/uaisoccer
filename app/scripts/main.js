/* jshint devel:true */
require.config({paths:{knockout: "../bower_components/knockout/dist/knockout"}});	
require(["knockout","scripts/championships/championship_viewmodel", "scripts/greeting/greeting"], function(ko, ChampionshipViewModel, greeting) {
	ko.applyBindings(ChampionshipViewModel());
	ko.applyBindings(greeting());

});

