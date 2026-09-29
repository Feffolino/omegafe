"""Pixel-art cover for Omega Flashlight FE Batteries, built from the mod's own 16x16 textures.
Outputs: icon.png (400x400, CurseForge project avatar), logo.png (256x256, in-jar mod logo),
banner.png (1600x900, description header)."""
import math
import os
from PIL import Image, ImageDraw

HERE = os.path.dirname(os.path.abspath(__file__))
REF = os.path.join(HERE, "ref")
TEX = os.path.join(HERE, "..", "src", "main", "resources", "assets", "omegafe", "textures", "item")

# 5x7 bitmap font (only needed glyphs)
FONT = {
    "A": ["01110", "10001", "10001", "11111", "10001", "10001", "10001"],
    "B": ["11110", "10001", "10001", "11110", "10001", "10001", "11110"],
    "C": ["01110", "10001", "10000", "10000", "10000", "10001", "01110"],
    "E": ["11111", "10000", "10000", "11110", "10000", "10000", "11111"],
    "F": ["11111", "10000", "10000", "11110", "10000", "10000", "10000"],
    "G": ["01110", "10001", "10000", "10111", "10001", "10001", "01111"],
    "H": ["10001", "10001", "10001", "11111", "10001", "10001", "10001"],
    "I": ["11111", "00100", "00100", "00100", "00100", "00100", "11111"],
    "L": ["10000", "10000", "10000", "10000", "10000", "10000", "11111"],
    "M": ["10001", "11011", "10101", "10101", "10001", "10001", "10001"],
    "N": ["10001", "11001", "10101", "10011", "10001", "10001", "10001"],
    "O": ["01110", "10001", "10001", "10001", "10001", "10001", "01110"],
    "R": ["11110", "10001", "10001", "11110", "10100", "10010", "10001"],
    "S": ["01111", "10000", "10000", "01110", "00001", "00001", "11110"],
    "T": ["11111", "00100", "00100", "00100", "00100", "00100", "00100"],
    "Y": ["10001", "10001", "01010", "00100", "00100", "00100", "00100"],
    " ": ["000", "000", "000", "000", "000", "000", "000"],
}

BOLT = [  # 7x12 lightning bolt
    "0001111",
    "0011110",
    "0011100",
    "0111000",
    "0111111",
    "1111110",
    "0001100",
    "0011000",
    "0010000",
    "0110000",
    "0100000",
    "1000000",
]


def text_width(s):
    return sum(len(FONT[c][0]) + 1 for c in s) - 1


def draw_text(img, s, x, y, color, shadow=(20, 16, 40, 255)):
    for dx, dy, col in ((1, 1, shadow), (0, 0, color)):
        cx = x + dx
        for ch in s:
            g = FONT[ch]
            for r, row in enumerate(g):
                for c, bit in enumerate(row):
                    if bit == "1":
                        img.putpixel((cx + c, y + dy + r), col)
            cx += len(g[0]) + 1


def background(w, h, beam_from, beam_dir, spread=0.32):
    img = Image.new("RGBA", (w, h))
    px = img.load()
    bx, by = beam_from
    ang0 = math.atan2(beam_dir[1], beam_dir[0])
    maxd = math.hypot(w, h)
    for y in range(h):
        for x in range(w):
            # deep navy with vignette
            vx, vy = (x - w / 2) / w, (y - h / 2) / h
            vig = 1 - min(1, (vx * vx + vy * vy) * 1.6)
            r, g, b = 12 + 14 * vig, 12 + 16 * vig, 28 + 34 * vig
            # warm flashlight cone
            dx, dy = x - bx, y - by
            d = math.hypot(dx, dy)
            if d > 0:
                da = abs((math.atan2(dy, dx) - ang0 + math.pi) % (2 * math.pi) - math.pi)
                if da < spread:
                    k = (1 - da / spread) ** 1.5 * max(0, 1 - d / maxd) * 0.55
                    r += (255 - r) * k
                    g += (225 - g) * k
                    b += (160 - b) * k
            # ordered dither to band the gradient like pixel art
            q = 6
            r, g, b = (int(v // q * q) for v in (r, g, b))
            px[x, y] = (r, g, b, 255)
    return img


def paste_sprite(dst, spr, x, y, scale, glow=None):
    s = spr.resize((spr.width * scale, spr.height * scale), Image.NEAREST)
    if glow:
        halo = Image.new("RGBA", dst.size, (0, 0, 0, 0))
        mask = s.split()[3]
        for off in range(1, 3):
            for ox, oy in ((-off, 0), (off, 0), (0, -off), (0, off)):
                halo.paste(Image.new("RGBA", s.size, glow + (90 // off,)), (x + ox, y + oy), mask)
        dst.alpha_composite(halo)
    dst.alpha_composite(s, (x, y))


def bolt_sprite(fill=(255, 226, 64, 255), edge=(255, 250, 200, 255)):
    h, w = len(BOLT), len(BOLT[0])
    im = Image.new("RGBA", (w + 2, h + 2), (0, 0, 0, 0))
    for r, row in enumerate(BOLT):
        for c, bit in enumerate(row):
            if bit == "1":
                for ox, oy in ((-1, 0), (1, 0), (0, -1), (0, 1)):
                    im.putpixel((c + 1 + ox, r + 1 + oy), (120, 70, 10, 255))
    for r, row in enumerate(BOLT):
        for c, bit in enumerate(row):
            if bit == "1":
                left_edge = c == 0 or row[c - 1] == "0"
                im.putpixel((c + 1, r + 1), edge if left_edge else fill)
    return im


def charge_pad(w):
    pad = Image.new("RGBA", (w, 4), (0, 0, 0, 0))
    d = ImageDraw.Draw(pad)
    d.rectangle([0, 0, w - 1, 3], fill=(54, 58, 70, 255))
    d.line([0, 0, w - 1, 0], fill=(110, 116, 132, 255))
    d.line([0, 3, w - 1, 3], fill=(28, 30, 38, 255))
    for i in range(2, w - 2, 3):
        d.point((i, 1), fill=(80, 230, 255, 255))
    return pad


def sparks(img, pts, col=(120, 240, 255, 255)):
    for x, y in pts:
        img.putpixel((x, y), col)
        for ox, oy in ((-1, 0), (1, 0), (0, -1), (0, 1)):
            img.putpixel((x + ox, y + oy), col[:3] + (140,))


def charge_bars(img, x, y, n_on, n=5):
    d = ImageDraw.Draw(img)
    for i in range(n):
        col = (90, 255, 120, 255) if i < n_on else (40, 60, 50, 255)
        d.rectangle([x, y - i * 3 - 1, x + 3, y - i * 3], fill=col)


def make_icon():
    S = 50  # low-res canvas, scaled x8 to 400
    img = background(S, S, beam_from=(-6, -6), beam_dir=(1, 1.1), spread=0.5)
    bat = Image.open(os.path.join(TEX, "rechargeable_large_battery.png")).convert("RGBA")
    img.alpha_composite(charge_pad(38), (6, 42))
    paste_sprite(img, bat, 9, 10, 2, glow=(120, 230, 255))
    sparks(img, [(5, 38), (45, 36), (43, 12), (7, 14)])
    img.resize((400, 400), Image.NEAREST).save(os.path.join(HERE, "icon.png"))
    img.resize((256, 256), Image.NEAREST).save(os.path.join(HERE, "logo.png"))
    img.resize((1024, 1024), Image.NEAREST).save(os.path.join(HERE, "icon_1024.png"))


def text_img(s, color):
    im = Image.new("RGBA", (text_width(s) + 1, 8), (0, 0, 0, 0))
    draw_text(im, s, 0, 0, color)
    return im


def make_banner():
    W, H = 200, 112  # x8 = 1600x896, padded to 1600x900
    img = background(W, H, beam_from=(W + 8, -8), beam_dir=(-1, 0.62), spread=0.34)
    floor = 98
    img.alpha_composite(charge_pad(108), (4, floor))
    x = 8
    for name, scale in (("small_battery", 1), ("medium_battery", 2), ("large_battery", 3)):
        spr = Image.open(os.path.join(TEX, "rechargeable_" + name + ".png")).convert("RGBA")
        paste_sprite(img, spr, x, floor - 15 * scale, scale, glow=(120, 230, 255))
        x += 16 * scale + 2
    paste_sprite(img, bolt_sprite(), 110, floor - 30, 2)
    sparks(img, [(6, 90), (112, 92), (44, 106), (52, 44), (20, 66), (118, 60)])
    t1 = text_img("OMEGA FLASHLIGHT", (255, 236, 190, 255))
    t2 = text_img("FE BATTERIES", (120, 230, 255, 255))
    tx = 100
    img.alpha_composite(t1, (tx, 16))
    img.alpha_composite(t2, (tx + (t1.width - t2.width) // 2, 27))
    hi = img.resize((W * 2, H * 2), Image.NEAREST)
    sub = text_img("RECHARGE IN ANY FE CHARGER", (205, 208, 235, 255))
    hi.alpha_composite(sub, (tx * 2 + (t1.width * 2 - sub.width) // 2, 82))
    out = hi.resize((1600, 896), Image.NEAREST)
    canvas = Image.new("RGBA", (1600, 900), out.getpixel((0, 895)))
    canvas.alpha_composite(out, (0, 0))
    canvas.save(os.path.join(HERE, "banner.png"))


if __name__ == "__main__":
    make_icon()
    make_banner()
    print("ok")
