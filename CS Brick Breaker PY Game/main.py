import pygame
import paddle
import ball
import block
import time
import random
import math

screenW, screenH = 1280, 720 #Screen size

backroundColor = [0,0,0]

offset = 30 #Paddle set up
PaddleW = 200
PaddleH = 25
PaddleX = screenW / 2 - PaddleW / 2
PaddleY = screenH - PaddleH - offset


pygame.init() #Display set up
pygame.display.set_caption("Brick Breaker")
Display = pygame.display.set_mode((screenW, screenH))
Display.fill(backroundColor)

player = paddle.Paddle(PaddleX, PaddleY, PaddleW, PaddleH)

ballSize = 12 #Ball parameters
ballX = PaddleX
ballY = PaddleY-20
ballSpeed = -4
ballMoveY = ballSpeed
ballMoveX = ballSpeed
balls = pygame.sprite.Group()
balls = ball.Ball(ballX, ballY, ballSize)
shiftX = 0 #Shift ball
shiftY = 0
#ballDraw = ball.Ball.draw(Display)

lives = 3 #Variables
score = 0

n = 10
blockW = screenW / n #128 per block #Block set up
blockH = blockW /4 + 10
blockColor = [6,130,183]
blockNewX = 10
block1Y = 100
numBlocks = 0
blk = False

#blocks init
'''blocks = pygame.sprite.Group()
blocks = []'''

#Setub all blocks for collide rect
'''
block1 = pygame.sprite.Group()
block1 = block.Block(10,block1Y,blockW, blockH, blockColor)
block1 = pygame.sprite.Group()
b1h = False
'''
blocks = pygame.sprite.Group()
#blocks.add(block.Block(10,block1Y,blockW, blockH, blockColor))

counter = 0
def displayText(text, color, posX, posY, size):
    str(text)
    font = pygame.font.SysFont('impact', size)
    textWrite = font.render(text, True, color)
    Display.blit(textWrite, (posX, posY))
def waitForKey():
    wait = True
    while wait:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                    pygame.quit()
                    GameOver = True
            keys = pygame.key.get_pressed()
            if keys[pygame.K_SPACE]:
                wait = False
def goto(line) :
    global lineNumber
    line = lineNumber

def scoreCheck(counter, score):
    score += 2

#Spawn blocks using loop
m=4
for y in range (m): #Spawn blocks
    for i in range (0, int(screenW), int(blockW)):
        blocks.add(block.Block(i+2, blockH*(y+2), blockW-4, blockH-4, blockColor))

#block1.draw(Display)

#blocks.draw = (14,14,14,14)

FPSClock = pygame.time.Clock()
FPS = 144

#Start screen
GameOver = False
Display.fill([130,140,230])
displayText('Brick Breaker', [200,230,100], 450,100, 80)
displayText('Use the mouse to move the paddle and bounce the ball. Try to break the bricks and not die??', [220,10,10], 120, 300,30)
#displayText('Survive for as long as you can', [220,10,10],450,400,30)
displayText('Press space to begin', [0,0,0],520,510, 30)
displayText('By: Mikul', [0, 0, 0, ], 610, 590, 17)
pygame.display.flip()
pygame.mixer.music.load("MiiFlute.OGG")
pygame.mixer.music.play(-1)
waitForKey()

while not GameOver: #Main game loop
    counter += 1/FPS
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            if GameOver == False:
                GameOver = True
    mouseX = pygame.mouse.get_pos()[0] #Player set up
    #mouseX = ballX #Paddle bot to test
    Display.fill(backroundColor)
    player.draw(Display)
    player.move(mouseX)

    balls.move(ballX, ballY) #pygame.draw.circle(Display, [0, 120, 120], [int(ballX), int(ballY)], ballSize)
    balls.draw(Display)

    #Old draw blocks
    '''
    for x in range(len(blocks)):
        blocks[x].draw(Display)
    '''
    #Draw blocks
    #block1.draw(Display)

    if ballX <= 0: #Boders
        ballMoveX = -ballSpeed
        pygame.mixer.music.load("GungaGinga.mp3")
        pygame.mixer.music.play(1)
        shiftX = -random.randint(-2,2)
        #shiftY = -shiftX
    elif ballX >= screenW:
        ballMoveX = ballSpeed
        pygame.mixer.music.load("GungaGinga.mp3")
        pygame.mixer.music.play(1)
        shiftX = random.randint(-2, 2)
        #shiftY = -shiftX
    if ballY <= 0:
        ballMoveY = -ballSpeed
        pygame.mixer.music.load("GungaGinga.mp3")
        pygame.mixer.music.play(1)
        shiftY = -random.randint(-2,2)
        #shiftX = -shiftY
    elif ballY >= screenH:
        #Hits ground
        #pygame.mixer.music.load("OOF.mp3")
        #pygame.mixer.music.play(1)
        pygame.mixer.music.load("YodaDeath.mp3")
        pygame.mixer.music.play(1)
        ballMoveY = ballSpeed
        score = score - 5 #Reduce score
        lives -= 1
        ballX = PaddleX
        ballY = PaddleY - 20
        balls.move(ballX, ballY)
        shiftX = 0
        shiftY = 0
        time.sleep(1)

    elif ballY <= PaddleY + PaddleW and ballY +ballSize >= PaddleY: #Hits paddle
        if (ballX >= mouseX or ballX <= mouseX) and (ballX + ballSize <= mouseX + PaddleW/2 and ballX + ballSize >= mouseX - PaddleW/2): # or ballX + ballSize >= mouseX - PaddleW/2
            ballMoveY = ballSpeed
            pygame.mixer.music.load("OOF.mp3")
            pygame.mixer.music.play(1)
            #pygame.mixer.music.load("YodaDeath.mp3")
            #pygame.mixer.music.play(1)


    ballX += ballMoveX #Coords for move ball
    ballX = ballX +shiftX
    ballY += ballMoveY
    ballY = ballY + shiftY
    #print(ballX)
    #print(ballY)


    displayText('Lives: %s'%lives, [90,190,90], 20, 10, 40) #Display
    displayText('Time: %.1f' %counter, [20,190,90], 1100, 10, 40)
    displayText('Score: %.0f' % score, [90, 190, 90], 570, 10, 40)
    if counter == 5:
        ballSpeed -= 1


    #Current colliderect working
    if pygame.sprite.spritecollide(balls, blocks, True):
        score += 10
        numBlocks += 1
        pygame.mixer.music.load("Zap.mp3")
        pygame.mixer.music.play(1)
        if ballY <= block1Y:
            ballMoveY = ballSpeed
            shiftY = random.randint(-2, 2)
        else:
            ballMoveY = -ballSpeed
            shiftY = -random.randint(-2, 2)
        if ballX <= blockNewX:
            ballMoveX = ballSpeed
        elif ballX >= (blockNewX + blockW):
            ballMoveX = -ballSpeed
    print(numBlocks)
    for block in blocks:
        block.draw(Display)

    #scoreCheck(counter, score) #Scoring doesnt work
    #score = counter/2 #increase score every 2 sec

    pygame.display.flip() #Game stuff
    pygame.display.update()
    FPSClock.tick(FPS)

    if numBlocks == 10 and not blk:
        ballSpeed -= 1
        blk = True

    if numBlocks >= 40:
        Display.fill([130, 140, 230])
        displayText('You Won!', [200, 230, 100], 520, 100, 80)
        displayText('Score: %.0f' % score, [250, 10, 10], 580, 260, 40)
        displayText('Thanks for playing',[250, 10, 10], 540, 340, 35)
        displayText('Press space to exit', [0, 0, 0], 550, 510, 30)
        displayText('By: Mikul', [0,0,0,], 640, 590,13)
        pygame.display.flip()
        pygame.mixer.music.load("WonPiano.mp3")
        pygame.mixer.music.play(-1)
        waitForKey()
        GameOver = True

    if (lives <= 0):
        #You died
        GameOver = True
        Display.fill([130, 140, 230])
        displayText('You Lost', [200, 230, 100], 520, 100, 80)
        displayText('Score: %.0f' % score, [250, 10, 10], 580, 260, 40)
        displayText('Thanks for trying',[250, 10, 10], 540, 340, 35)
        displayText('Press space to exit', [0, 0, 0], 550, 510, 30)
        displayText('By: Mikul', [0,0,0,], 640, 590,13)
        pygame.display.flip()
        pygame.mixer.music.load("DarkPiano.OGG")
        pygame.mixer.music.play(-1)
        waitForKey()
        #goto(96)
        GameOver = True
    #balls.hit()
    '''
    if balls.collide():
        print("collide")
        score += 10
        '''
pygame.quit()