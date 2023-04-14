%fix 23.04.13
%23:24 bugfix


function drawClock()
    % Initialize
    clf;
    axis([-1 1 -1 1], "square");
    axis("off");

    % Draw clock face
    drawClockFace();

    % Draw hour, minute, and second hands
    global hourHandLength minuteHandLength secondHandLength;
    hourHandLength = 0.5;
    minuteHandLength = 0.8;
    secondHandLength = 0.9;
    drawHands(0, 0, 0);

    % Update position of hour, minute, and second hands every second
    while true
        t = clock();
        updateTime(mod(t(4), 12), t(5), t(6));
        pause(0.01);
    endwhile
endfunction

function updateTime(hour, minute, second)
    global hourHandLength minuteHandLength secondHandLength;

    clf;
    axis([-1 1 -1 1], "square");
    axis("off");

    drawClockFace();

    hourAngle = (30*(mod(hour, 12) + minute/60))/180*pi;
    minuteAngle = (6*(mod(minute, 60) + second/60))/180*pi;
    secondAngle = (6*second)/180*pi;

drawHands(hourAngle, minuteAngle, secondAngle);
endfunction

function drawHands(hourAngle, minuteAngle, secondAngle)
    global hourHandLength minuteHandLength secondHandLength;

    % Draw hour hand
    hourHand = [0 0; hourHandLength*cos(hourAngle - pi/2) hourHandLength*sin(hourAngle - pi/2)];
    line(hourHand(:,1), hourHand(:,2), "linewidth", 3);

    % Draw minute hand
    minuteHand = [0 0; minuteHandLength*cos(minuteAngle - pi/2) minuteHandLength*sin(minuteAngle - pi/2)];
    line(minuteHand(:,1), minuteHand(:,2), "linewidth", 2);

    % Draw second hand
    secondHand = [0 0; secondHandLength*cos(secondAngle - pi/2) secondHandLength*sin(secondAngle - pi/2)];
    line(secondHand(:,1), secondHand(:,2), "linewidth", 1, "color", "red");
endfunction

% Refresh time every second
while (1)
pause(0.1);
t = clock;

% Calculate position of hour, minute, and second hands
hourAngle = (30*(mod(t(4),12) + t(5)/60))/180*pi;
minuteAngle = (6*(mod(t(5),60) + t(6)/60))/180*pi;
secondAngle = (6*t(6))/180*pi;

% Calculate position of hour, minute, and second hands and draw them
hourHand = createHand(hourAngle, hourHandLength, hourHandWidth);
plot(hourHand(:,1), hourHand(:,2),'k','LineWidth',hourHandWidth);
end