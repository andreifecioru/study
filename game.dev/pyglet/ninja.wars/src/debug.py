import pyglet
from app import App


class DebugOverlay:
    _slots = [
        pyglet.text.Label(
            '',
            font_name='Times New Roman', font_size=12,
            x=10, y=10,
            anchor_x='left', anchor_y='bottom'),

        pyglet.text.Label(
            '',
            font_name='Times New Roman', font_size=12,
            x=10, y=25,
            anchor_x='left', anchor_y='bottom'),

        pyglet.text.Label(
            '',
            font_name='Times New Roman', font_size=12,
            x=10, y=40,
            anchor_x='left', anchor_y='bottom'),
    ]

    @staticmethod
    def render():
        [label.draw() for label in DebugOverlay._slots]

    @staticmethod
    def display(slot_idx, text):
        DebugOverlay._slots[slot_idx].text = text

    @staticmethod
    def start():
        App.add_event_listener('on_draw', DebugOverlay.render)
