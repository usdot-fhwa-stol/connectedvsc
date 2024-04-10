/**
 * Created by martzth on 4/12/2015.
 * Updated 3/2017 by martzth
 */

/**
 * DEFINE GLOBAL VARIABLES
 */

	var canvas = document.getElementById('circle');
	var ctx = canvas.getContext('2d');
	var circles = [];

	var cr = 100;
	var cx = 140;
	var cy = 130;
	var slice = 22.5;

	var rad, nxtRad;

/**
 * DEFINE FUNCTIONS
 */

	//create circle outline
	ctx.beginPath();
	ctx.arc(cx, cy, cr, 0, 2 * Math.PI);
	ctx.stroke();

	//fixes a problem where double clicking causes text to get selected on the canvas
	canvas.onselectstart = function () { return false; }

	//to use degrees in the functions
	function toRadians(deg) {
		return (deg-90) * Math.PI / 180
	}

	/**
	 * Purpose: draw circle and slices
	 * @params: canvas context and size/color paramaters
	 * @event: draw
	 */

	var draw = function (ctx, x, y, radius, strokestyle, fillcolor, degrees, circles) {

		rad = toRadians(degrees);
		nxtRad = toRadians(degrees + slice)

		ctx.beginPath();
			ctx.moveTo(x,y);
			ctx.arc(x,y,radius,rad,nxtRad);
			ctx.lineTo(x,y);
		ctx.closePath();
			ctx.fillStyle = fillcolor;
			ctx.fill();
			ctx.strokeStyle = strokestyle;
			ctx.stroke();
			ctx.fillStyle = "black";

			//shifts and writing degree text
			if (degrees == 0){
				ctx.fillText(degrees,(radius * (Math.cos(toRadians(degrees))))+x-2, (radius * (Math.sin(toRadians(degrees))))+y-8);
			} if (degrees <= 67.5 && degrees != 0){
				ctx.fillText(degrees,(radius * (Math.cos(toRadians(degrees))))+x+5, (radius * (Math.sin(toRadians(degrees))))+y-4);
			} if (degrees >= 292.5){
				ctx.fillText(degrees,(radius * (Math.cos(toRadians(degrees))))+x-32, (radius * (Math.sin(toRadians(degrees))))+y-2);
			} if (degrees == 270) {
				ctx.fillText(degrees,(radius * (Math.cos(toRadians(degrees))))+x-25, (radius * (Math.sin(toRadians(degrees))))+y+2);
			} if (degrees == 90) {
				ctx.fillText(degrees,(radius * (Math.cos(toRadians(degrees))))+x+6, (radius * (Math.sin(toRadians(degrees))))+y+2);
			}

			if (degrees > 90 && degrees < 180 ){
				ctx.fillText(degrees,(radius * (Math.cos(toRadians(degrees))))+x+5, (radius * (Math.sin(toRadians(degrees))))+y+8);
			} if (degrees > 180 && degrees < 270) {
				ctx.fillText(degrees,(radius * (Math.cos(toRadians(degrees))))+x-32, (radius * (Math.sin(toRadians(degrees))))+y+10);
			} if (degrees == 180) {
				ctx.fillText(degrees,(radius * (Math.cos(toRadians(degrees))))+x-8, (radius * (Math.sin(toRadians(degrees))))+y+14);
			}



			drawNode(ctx, x, y, radius, rad);

			if (d == 337.5){
				drawNode(ctx, cx, cy, cr, nxtRad)
			}

	};

	var drawNode = function (ctx, x, y, radius, radians){

		 ctx.beginPath();
			ctx.arc((radius * (Math.cos(radians)))+x, (radius * (Math.sin(radians)))+y, 4, 0, 2 * Math.PI);
			ctx.fillStyle = "blue";
			ctx.fill();
		 ctx.closePath();

	}

	var drawCircle = function (ctx, x, y, radius, strokestyle, fillcolor, degrees, circles) {
		draw(ctx, x, y, radius, strokestyle, fillcolor, degrees);
		var circle = new Circle(x, y, radius, degrees);
		circles.push(circle);
	};

	var Circle = function(x, y, radius, degrees) {

		rad = toRadians(degrees);
		nxtRad = toRadians(degrees + slice)

		this.theta = rad;
		this.nxtTheta = nxtRad;
		this.x1 = x;
		this.x2 = (radius * (Math.cos(rad))) + x;
		this.x3 = (radius * (Math.cos(nxtRad))) + x;
		this.y1 = y;
		this.y2 = (radius * (Math.sin(rad))) + y;
		this.y3 = (radius * (Math.sin(nxtRad))) + y;
		this.active = false;
	};

	for (d=0; d < 360; (d =d + 22.5)){
		drawCircle(ctx, cx, cy, cr, "black", "white", d, circles);
	}

/**
 * Purpose: fill/unfill and activate slice if clicked
 * @params: click event
 * @event: fill/unfill and activate slice if clicked
 */

$('#circle').click(function (e) {
    var clickedX = e.pageX - $(this).offset().left;
    var clickedY = e.pageY - $(this).offset().top;
     
    for (var i = 0; i < circles.length; i++) {
  
        if (is_in_triangle(clickedX, clickedY, circles[i].x1, circles[i].y1, circles[i].x2, circles[i].y2, circles[i].x3, circles[i].y3)) {
	        if (circles[i].active){
	        	ctx.beginPath();
		        	ctx.moveTo(circles[i].x1,circles[i].y1);
		        	ctx.arc(circles[i].x1,circles[i].y1,cr,circles[i].theta,circles[i].nxtTheta);
		        	ctx.lineTo(circles[i].x1,circles[i].y1);
		        ctx.closePath();	
		        	ctx.fillStyle = "white";
		        	ctx.fill();
		        	ctx.strokeStyle = "black";
		        	ctx.stroke();
		        	
	        	circles[i].active = false;
	        	clickMapping(circles)

	        	
	        } else {        	
	        	ctx.beginPath();
		        	ctx.moveTo(circles[i].x1,circles[i].y1);
		        	ctx.arc(circles[i].x1,circles[i].y1,cr,circles[i].theta,circles[i].nxtTheta);
		        	ctx.lineTo(circles[i].x1,circles[i].y1);
		        ctx.closePath();	
		        	ctx.fillStyle = "LightSkyBlue";
		        	ctx.fill();
		        	ctx.strokeStyle = "black";
		        	ctx.stroke();
		        	
	            circles[i].active = true;
	            clickMapping(circles)
	        }
	        
		    ctx.beginPath();
		        ctx.arc((cr * (Math.cos(circles[i].theta)))+circles[i].x1, (cr * (Math.sin(circles[i].theta)))+circles[i].y1, 4, 0, 2 * Math.PI);
		        ctx.fillStyle = "blue";
		        ctx.fill();
		    ctx.closePath();
		    
		    ctx.beginPath();
			    ctx.arc((cr * (Math.cos(circles[i].nxtTheta)))+circles[i].x1, (cr * (Math.sin(circles[i].nxtTheta)))+circles[i].y1, 4, 0, 2 * Math.PI);
			    ctx.fillStyle = "blue";
			    ctx.fill();
		    ctx.closePath();
 
        }
    }
});


/**
 * Purpose: determine if click action occurs within a slice
 * @params: click event and slice x/y coordinates
 * @return: true/false
 */

function is_in_triangle (px,py,ax,ay,bx,by,cx,cy){

	var v0 = [cx-ax,cy-ay];
	var v1 = [bx-ax,by-ay];
	var v2 = [px-ax,py-ay];

	var dot00 = (v0[0]*v0[0]) + (v0[1]*v0[1]);
	var dot01 = (v0[0]*v1[0]) + (v0[1]*v1[1]);
	var dot02 = (v0[0]*v2[0]) + (v0[1]*v2[1]);
	var dot11 = (v1[0]*v1[0]) + (v1[1]*v1[1]);
	var dot12 = (v1[0]*v2[0]) + (v1[1]*v2[1]);

	var invDenom = 1/ (dot00 * dot11 - dot01 * dot01);

	var u = (dot11 * dot02 - dot01 * dot12) * invDenom;
	var v = (dot00 * dot12 - dot01 * dot02) * invDenom;

	return ((u >= 0) && (v >= 0) && (u + v < 1));
}


/**
 * Purpose: function to log slice status
 * @params: circle
 * @event: loops through the slices and logs the status
 *
 * CAN BE DEPRECATED
 */

function clickMapping(circles){
	for (var segment=0; segment<circles.length; segment++) {
		console.log(segment, ": " ,circles[segment].active)
	}
}









