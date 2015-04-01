var Observable = function(){
	
};
Observable.prototype.get = function(varName){
	return this.vars[varName];
};
Observable.prototype.on = function(eventName, fn){
	if(!this.listeners){
		this.listeners = {};
	}
	
	if(!this.listeners[eventName]){
		this.listeners[eventName] = [];
	}
	this.listeners[eventName].push(fn);
};
Observable.prototype.off = function(eventName, fn){
	if(this.listeners && this.listeners[eventName]){
		var eventListeners = this.listeners[eventName];
		var index = eventListeners.indexOf(fn);
		eventListeners.splice(index, 1);
	}
};
Observable.prototype.trigger = function(eventName, args){
	if(this.listeners && this.listeners[eventName]){
		var eventListeners = this.listeners[eventName];
		for(var i = 0; i < eventListeners.length; i++){
			eventListeners[i].apply(this, args);
		}
	}
};
Observable.prototype.update = function(message){
	var self = this;
	var data = JSON.parse(message);
	
	var throwAggEvent = Object.keys(data).length > 1 ? true : false;
	
	if(!self.vars){
		self.vars = {};
	}
	
	for(var key in data){
		if(self.vars[key] != data[key]){
			self.vars[key] = data[key];
			
			if(!throwAggEvent){
				var eventName = key + "-changed";
				self.trigger(eventName, [self.vars[key]]);
			}
		}
	}
	
	if(throwAggEvent){
		self.trigger('changed', []);
	}
};
Observable.prototype.set = function(key, val){
	this.vars[key] = val;
	var eventName = key + "-changed";
	this.trigger(eventName, [this.vars[key]]);
};

var Component = function(){
	this.waitingMessages = [];
	this._bindEvents();
};

Component.prototype = Object.create(Observable.prototype);
Component.prototype.putMsg = function(command){
	var argsStart = command.originalMessage.indexOf("(");
	var fn = command.originalMessage.substring(0, argsStart);
		
	if(typeof this[fn] == "function"){
		this[fn](command.content);
	}
};
Component.prototype._bindEvents = function () {
    var self = this;
    for (var event in this.events) {
        var splits = event.split(" ");
        var actionsStr = splits[0];
        splits.splice(0, 1);
        var selector = splits.join(" ");
        var actions = actionsStr.split(",");

        var callback = this.events[event];

        jQuery(selector).on(actions.join(" "), (function (callback) {
            return function (event) {
                callback.apply(self, [event, self]);
            }
        })(callback));
    }
};

var Page = function (options) {
	Component.apply(this, arguments);
    this.init();
};

Page.prototype = Object.create(Component.prototype);
Page.prototype.device = null;
Page.prototype.name = null;
Page.prototype.init = function () {
    //BIND EVENTS
    this.device = new DeviceDetect().getDevice();
    this._bindEvents();
    this._createPopups();
};
Page.prototype._bindEvents = function () {
    var self = this;
    for (var event in this.events) {
        var splits = event.split(" ");
        var actionsStr = splits[0];
        splits.splice(0, 1);
        var selector = splits.join(" ");
        var actions = actionsStr.split(",");

        var callback = this.events[event];

        jQuery(selector).on(actions.join(" "), (function (callback) {
            return function (event) {
                callback.apply(self, [event, self]);
            }
        })(callback));
    }
};
Page.prototype._createPopups = function () {
    var popupMethod;

    switch (this.device) {
        case "mobile":
            popupMethod = 'slidePanel';
            break;
        case "tablet":
        case "desktop":
            popupMethod = 'popover';
            break;
    }

    var popupButtons = jQuery('button[data-popup]')
    popupButtons.each(function () {
        var $this = jQuery(this);
        var selector = $this.attr('data-popup');
        var placement = $this.attr('data-placement');
        var container = $this.attr('data-container');
        var title = $this.attr('data-title');
        var height = $this.attr('data-height');
        $this[popupMethod]({
            html: true,
            container: ".page",
            placement: placement,
            container: container,
            title: title,
            height: height,
            content: function () {
                return jQuery(selector).children();
            }
        }).on('show.bs.popover', function () {
            var self = this;
            popupButtons.filter(function () {
                return this != self;
            }).popover('hide');
        });
    });
};
Page.prototype.events = {
};
