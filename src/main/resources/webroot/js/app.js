var stompClient = null;
var socket = new SockJS('/graphdata-websocket');
stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
	stompClient.subscribe('/topic/data', function(message) {
		alert(JSON.parse(message.body).xValue);
		sampling = JSON.parse(message.body).columnA;
		alert(sampling);
		sampling2 = JSON.parse(message.body).columnB;
		alert(sampling2);
		drawMyGraph(sampling, sampling2);
	});
});

var drawMyGraph = function(sampling, sampling2) {
	alert(sampling);
	alert(sampling2);

	var ctx = demo.getContext('2d'), w = demo.width, h = demo.height, px = 0, opx = 0, speed = 2, py = h * 0.8, opy = py, scanBarWidth = 40;

	ctx.strokeStyle = '#FFFFFF';
	ctx.lineWidth = 2;

	var myVar = setInterval(function() {
		myTimer()
	}, 50);

	var sampling = [];
	var sampling2 = [];
	var index = 0;
	function myTimer() {
		if (index >= sampling.length)
			index = 0
		py = sampling[index];
		py2 = sampling2[index];
		index++
	}

	var ctx2 = demo.getContext('2d'), py2 = h * 0.8, opy2 = py2;

	ctx2.strokeStyle = '#00bd12';
	ctx2.lineWidth = 2;

	loop();

}

function loop() {

	px += speed;
	ctx.clearRect(px, 0, scanBarWidth, h);
	ctx.beginPath();
	ctx.moveTo(opx, opy);
	ctx.lineTo(px, py);
	ctx.strokeStyle = 'red';
	ctx.stroke();
	ctx2.beginPath();
	ctx2.moveTo(opx, opy2);
	ctx2.lineTo(px + 2, py2);
	ctx2.strokeStyle = 'green';
	ctx2.stroke();

	opx = px;
	opy = py;
	opy2 = py2

	if (opx > w) {
		px = opx = -speed;
	}
	requestAnimationFrame(loop);
}