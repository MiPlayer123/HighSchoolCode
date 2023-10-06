import pygame
import block

blocks = pygame.sprite.Group()
blocks = []

class Ball(pygame.sprite.Sprite):
    def __init__(self, posx, posy, radius):
        super().__init__()
        pygame.sprite.Sprite.__init__(self)
        #self.rect = self.image.get_rect()
        self.rect = pygame.Rect(posx, posy, radius, radius) #self.rect =
        self.posx = posx
        self.posy = posy
        self.radius = radius
    def draw (self, Display):
        pygame.draw.circle(Display, [110,130,170], [int(self.posx), int(self.posy)], self.radius)
    def move(self, ballX, ballY):
        self.posx = ballX
        self.posy = ballY
        #pygame.Rect.move(self.rect,ballX,ballY)
        self.rect.topleft = (ballX, ballY)
        self.posx, self.posy = self.rect.topleft
    def hit(self):
        hit_block = pygame.sprite.groupcollide(blocks, Ball, False, True)
        if hit_block:
            return True
        else:
            return False
    def collide(self):
        if self.hit():
            print("collision")
            score += 10
            blocks.kill(blocks)