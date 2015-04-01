var Controller = function(options){
	var self = {};
	var settings = {}
	
	$.extend(settings, options);
	
	self.putMsg = function(msg){
		try{
			var command = [msg.target[0].class];
		}catch(e){
			command = $(msg.currentTarget).attr('class').split(" ");
		}
		var content = msg.content;
		for(i=0;i<command.length;i++){
			switch(command[i]) { 
				case 'image1':
					document.getElementById('img1').openImage(1);
	  				break;
	  			case 'image2':
					document.getElementById('img1').openImage(0);
	  				break;
				default:
					console.log('unhandled msg in mainscreen.js : '+command); 
			}
		}
	}
	

	return self;
}