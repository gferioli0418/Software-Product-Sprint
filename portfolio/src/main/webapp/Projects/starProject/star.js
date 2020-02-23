function star(){
  this.x = random(-width,width);
  this.y = random(-height,height);
  this.z = random(width);
  this.pz= this.z;



  this.move = function(){

    this.z = this.z-speed;
    // this.sx = map(this.x/this.z,0,1,0,width);
    // this.sy = map(this.y/this.z,0,1,0,height);
    // this.r = map(this.z,0,width, 15,0);
    if(this.z<1){
      this.z = width;
      this.x = random(-width,width);
      this.y = random(-height,height);
      this.pz = this.z;

    }

  }
  this.show = function(){
    this.sx = map(this.x/this.z,0,1,0,width);
    this.sy = map(this.y/this.z,0,1,0,height);

    this.d = map(this.z,0,width, 15,0);

    this.px = map(this.x/this.pz,0,1,0,width);
    this.py = map(this.y/this.pz,0,1,0,height);
    fill(255);
    noStroke();
    ellipse(this.sx,this.sy,this.d);

    // stroke(255);
    fill(255,255,255,100);
    // strokeWeight(.1);
    this.pz =this.z;
    triangle(this.sx-this.d/2,this.sy-this.d/2,this.sx+this.d/2,this.sy+this.d/2,this.px,this.py);
    // line(this.px,this.py,this.sx,this.sy);
  }
}
