-----이 라인은 지워 줘-----
-----수정 다시 해 봄------
-----11:47-----


function drawClock()
    % Initialize
    clf;
    axis([-1 1 -1 1], "square");
    axis("off");

    % Draw clock face
    drawClockFace();

    % Draw hour, minute, and second hands
    global hourHandLength minuteHandLength secondHandLength hourHandWidth;
    hourHandLength = 0.5;
    minuteHandLength = 0.8;
    secondHandLength = 0.9;
    hourHandWidth = 3; % Add hourHandWidth as a global variable
    drawHands(0, 0, 0);

    % Update position of hour, minute, and second hands every second
    while true
        t = clock();
        updateTime(mod(t(4), 12), t(5), t(6));
        pause(0.01);
    end
end

function updateTime(hour, minute, second)
    global hourHandLength minuteHandLength secondHandLength hourHandWidth;

    clf;
    axis([-1 1 -1 1], "square");
    axis("off");

    drawClockFace();

hourAngle = (30*(mod(hour, 12) + minute/60))/180*pi;
minuteAngle = (6*(mod(minute, 60) + second/60))/180*pi;
secondAngle = (6*second)/180*pi;

drawHands(hourAngle, minuteAngle, secondAngle);
