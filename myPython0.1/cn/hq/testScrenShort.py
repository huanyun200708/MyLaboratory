import ImageGrab

im = ImageGrab.grab()

addr = "D:/a.jpg"
im.save(addr,'jpeg')