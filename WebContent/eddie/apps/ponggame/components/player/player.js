var Player = function(){
	$(function() {
		var playingField = $('#player').height();
		$('#controller').draggable(
		{
			axis: "y",
			start: function(){
			},
			drag: function(e) {
				var position = $('#controller').position().top;	
				//if(position >= 0 && position <= playingField){
					eddie.putLou("", "getControllerPosition(" + position + ")");
					console.log(position);
				//}
			},
			stop: function(ev){
				console.log('Stop');
			}
		});
	});
}
