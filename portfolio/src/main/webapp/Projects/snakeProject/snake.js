function Snake() {

  this.x = 300;
  this.y = 300;
  this.xspeed =0;
  this.yspeed =1;
  this.total = 0;
  this.tail = [];

  this.show = function() {
    fill(255);

    for(var i =0; i<this.tail.length;i++){
      rect(this.tail[i].x,this.tail[i].y,scl,scl);
    }

    rect(this.x,this.y,scl,scl);
  }
  
  this.eat= function(pos) {
    var d = dist(this.x,this.y,pos.x,pos.y);

    if(d<5){
      this.total ++;
      return true;
    }else{
      return false;
    }
  }

  this.move = function() {
    if(this.total===this.tail.length){
      for(var i=0;i<this.tail.length-1;i++){
        this.tail[i]=this.tail[i+1];
      }
    }

    this.tail[this.total-1]=createVector(this.x,this.y);

    this.x = this.x + this.xspeed*scl;
    this.y = this.y + this.yspeed*scl;

    this.x = constrain(this.x,0,width-scl);
    this.y = constrain(this.y,0,height-scl);
  }

  this.direction = function(xspeed,yspeed){
    this.xspeed = xspeed;
    this.yspeed = yspeed;
  }

  this.destroy = function(){
    for(i=0;i<this.tail.length;i++){
      var pos = this.tail[i];
      var d= dist(this.x,this.y,pos.x,pos.y);
      if(d<5){
        this.total = 0;
        this.tail = [];
      }
    }
  }
}
