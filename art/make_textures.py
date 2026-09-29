"""Original 16x16 item textures for the rechargeable batteries (MIT, drawn from scratch)."""
import os
from PIL import Image

HERE = os.path.dirname(os.path.abspath(__file__))
OUT = os.path.join(HERE, "..", "src", "main", "resources", "assets", "omegafe", "textures", "item")

OUTLINE = (24, 26, 32, 255)
CAP = (190, 196, 204, 255)
CAP_DARK = (120, 126, 136, 255)
CELL = (56, 186, 92, 255)
CELL_HI = (122, 236, 140, 255)
CELL_LO = (34, 122, 62, 255)
BAND = (40, 44, 54, 255)
BOLT = (255, 226, 70, 255)
BOLT_HI = (255, 248, 190, 255)


def battery(x0, y0, w, h, terminals, bolt):
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    p = img.putpixel
    x1, y1 = x0 + w - 1, y0 + h - 1
    # terminals on top
    for tx, tw in terminals:
        for x in range(tx, tx + tw):
            p((x, y0 - 2), OUTLINE)
            p((x, y0 - 1), CAP)
        p((tx + tw - 1, y0 - 1), CAP_DARK)
    # body
    for y in range(y0, y1 + 1):
        for x in range(x0, x1 + 1):
            if x in (x0, x1) or y in (y0, y1):
                p((x, y), OUTLINE)
            elif x == x0 + 1:
                p((x, y), CELL_HI)
            elif x >= x1 - 1:
                p((x, y), CELL_LO)
            else:
                p((x, y), CELL)
    # dark band under the cap
    for x in range(x0 + 1, x1):
        p((x, y0 + 1), BAND)
    # bolt symbol
    if bolt:
        cx, cy = x0 + w // 2, y0 + h // 2
        if bolt == 1:
            pts = [(1, -3), (0, -2), (0, -1), (-1, -1), (0, 0), (1, 0), (0, 1), (-1, 2)]
        else:
            rows = ["  ##", " ## ", "####", " ## ", "##  "]
            pts = [(c - 2, r - 2) for r, row in enumerate(rows) for c, ch in enumerate(row) if ch == "#"]
        for i, (dx, dy) in enumerate(pts):
            p((cx + dx, cy + dy), BOLT_HI if i < 2 else BOLT)
    return img


def main():
    os.makedirs(OUT, exist_ok=True)
    sprites = {
        "small": battery(5, 5, 6, 10, [(7, 2)], 1),
        "medium": battery(4, 3, 8, 12, [(6, 4)], 1),
        "large": battery(2, 3, 12, 12, [(4, 3), (9, 3)], 2),
    }
    for name, img in sprites.items():
        img.save(os.path.join(OUT, f"rechargeable_{name}_battery.png"))
        img.resize((256, 256), Image.NEAREST).save(os.path.join(HERE, "ref", f"preview_{name}.png"))
    sheet = Image.new("RGBA", (16 * 3 * 16 + 64, 256), (40, 44, 60, 255))
    for i, name in enumerate(sprites):
        sheet.alpha_composite(sprites[name].resize((256, 256), Image.NEAREST), (i * 272, 0))
    sheet.save(os.path.join(HERE, "ref", "preview_sheet.png"))
    print("ok")


if __name__ == "__main__":
    main()
