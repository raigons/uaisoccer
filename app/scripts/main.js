/* jshint devel:true */
require.config({
	paths:{
		knockout: "../bower_components/knockout/dist/knockout"
	}
});	
require(["knockout",
	"scripts/championships/championship_viewmodel"], function(ko, ChampionshipViewModel) {
	ko.applyBindings(ChampionshipViewModel());
});

