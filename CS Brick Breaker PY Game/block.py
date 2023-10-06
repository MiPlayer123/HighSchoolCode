import pygame

class Block(pygame.sprite.Sprite):
    def __init__(self, posx, posy, width, height, color):
        pygame.sprite.Sprite.__init__(self)
        super().__init__()
        #self.rect = self.image.get_rect()
        self.rect = pygame.Rect(posx, posy, width, height)
        self.posx = posx
        self.posy = posy
        self.height = height
        self.width = width
        self.color = color
    def draw (self, Display):
        pygame.draw.rect(Display, self.color, [self.posx, self.posy, self.width, self.height])