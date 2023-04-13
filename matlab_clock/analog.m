%fix 23.04.13


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
endwhile
I hope this helps! Let me know if you have any other questions.function demo()
  % 초기화
  clf;
  axis([-1 1 -1 1], "square");
  axis("off");
  
  % 시계판 그리기
  drawClockFace();
  
  % 시, 분, 초침 그리기
  global hourHandLength minuteHandLength secondHandLength;
  hourHandLength = 0.5;
  minuteHandLength = 0.8;
  secondHandLength = 0.9;
  drawHands(0, 0, 0);
  
  % 매 초마다 시, 분, 초침 위치 업데이트
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
  
  % 시침 그리기
  hourHand = [0 0; hourHandLength*cos(hourAngle - pi/2) hourHandLength*sin(hourAngle - pi/2)];
  line(hourHand(:,1), hourHand(:,2), "linewidth", 3);
  
  % 분침 그리기
  minuteHand = [0 0; minuteHandLength*cos(minuteAngle - pi/2) minuteHandLength*sin(minuteAngle - pi/2)];
  line(minuteHand(:,1), minuteHand(:,2), "linewidth", 2);
  
  % 초침 그리기
  secondHand = [0 0; secondHandLength*cos(secondAngle - pi/2) secondHandLength*sin(secondAngle - pi/2)];
  line(secondHand(:,1), secondHand(:,2), "linewidth", 1, "color", "red");
endfunction

% 시간 refresh
while (1)
    pause(0.1);
    t = clock;

    % 시침, 분침, 초침의 위치 계산
hourAngle = (30*(mod(t(4),12) + t(5)/60))/180*pi;
minuteAngle = (6*(mod(t(5),60) + t(6)/60))/180*pi;
secondAngle = (6*t(6))/180*pi;



% 시침, 분침, 초침의 위치 계산 후 그리기
hourHand = createHand(hourAngle, hourHandLength, hourHandWidth);
plot(hourHand(:,1), hourHand(:,2), 'k', 'LineWidth', hourHandWidth);
minuteHand = createHand(minuteAngle, minuteHandLength, minuteHandWidth);
plot(minuteHand(:,1), minuteHand(:,2), 'k', 'LineWidth', minuteHandWidth);
secondHand = createHand(secondAngle, secondHandLength, secondHandWidth);
plot(secondHand(:,1), secondHand(:,2), 'r', 'LineWidth', secondHandWidth);
end

% 침 생성 함수
function [hand] = createHand(angle, length, width)
hand = [0 0; lengthcos(angle) lengthsin(angle)];
hand = [hand; length*0.2*cos(angle+pi/2) length*0.2*sin(angle+pi/2)];
hand = [hand; lengthcos(angle) lengthsin(angle)];
hand(:,3) = width;
end

