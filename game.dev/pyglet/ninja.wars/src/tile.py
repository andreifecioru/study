import pyglet

class Tile:
    _hitbox_top_offset = 20
    _hitbox_bottom_offset = 20
    _hitbox_left_offset = 20
    _hitbox_right_offset = 20

    def __init__(self, img_source, x, y, batch=None, group=None):
        sprite_image = pyglet.image.load(img_source)
        self._sprite = pyglet.sprite.Sprite(sprite_image, x, y, batch=batch, group=group)
        self.hitbox = pyglet.math.Vec2(self.x, self.y)

    @property
    def x(self):
        return self._sprite.x
    @x.setter
    def x(self, value):
        self._sprite.x = value
        self.hitbox = pyglet.math.Vec2(value, self.hitbox.y)

    @property
    def y(self):
        return self._sprite.y
    @y.setter
    def y(self, value):
        self._sprite.y = value
        self.hitbox = pyglet.math.Vec2(self.hitbox.x, value)

    @property
    def width(self):
        return self._sprite.width

    @property
    def height(self):
        return self._sprite.height

    def move(self, direction, speed):
        self.x += direction.x * speed
        self.y += direction.y * speed

    def collides_with(self, other):
        # Check for collision on the X axis
        left_s = self.hitbox.x + Tile._hitbox_left_offset
        right_s = left_s + self.width - Tile._hitbox_right_offset
        left_o = other.hitbox.x + Tile._hitbox_left_offset
        right_o = left_o + other.width - Tile._hitbox_right_offset
        x_collision = left_o < left_s < right_o or left_o < right_s < right_o

        # Check for collision on the Y axis
        top_s = self.hitbox.y - Tile._hitbox_top_offset
        bottom_s = top_s - self.height + Tile._hitbox_bottom_offset
        top_o = other.hitbox.y - Tile._hitbox_top_offset
        bottom_o = top_o - other.height + Tile._hitbox_bottom_offset
        y_collision = bottom_o < top_s < top_o or bottom_o < bottom_s < top_o

        return x_collision and y_collision


class Rock(Tile):
    def __init__(self, x, y, batch=None, group=None):
        super().__init__('./graphics/test/rock.png', x, y, batch, group)


