		var dps = [];
		var updateInterval = 100;
		var dataLength = 500; // number of dataPoints visible at any point

		var colA, colB, i;

		var connect = function(){
			var stompClient = null;
			var socket = new SockJS('/graphdata-websocket');
			stompClient = Stomp.over(socket);
			alert("Before");
			stompClient.connect({}, function(frame) {
				alert(frame);
				stompClient.subscribe('/topic/data', function(message) {
					alert(JSON.parse(message.body).xValue);
					colA = JSON.parse(message.body).columnA;
					colB = JSON.parse(message.body).columnB;
					updateChart(JSON.parse(message.body).columnA, JSON.parse(message.body).columnB);
				});
			});
			
		}

		var chart = new CanvasJS.Chart("chartContainer",{
			title :{
				text: "EKG Real-time Reconstruction"
			},			
			data: [{
				type: "line",
				dataPoints: dps 
			}]
		});



		var updateChart = function (colA,colB) {

			for (i = 0; i < colA.length; i++) {

				dps.push({
					y: colA[i]
				},
				{
					y: colB[i]
				});

			}
			

			if (dps.length > dataLength)
			{
				dps.shift();				
			}
			
			chart.render();		

		};


// update chart after specified time. 
//setInterval(function(){updateChart()}, updateInterval); 

