import numpy as np
import cv2
from matplotlib import pyplot as plt
img = cv2.imread('F:/codeFile/python/search.png',0) # queryImage

ret,thresh = cv2.threshold(img,127,255,0)
contours = cv2.findContours(thresh, 1, 2)
cnt = contours[0]
x,y,w,h = cv2.boundingRect(cnt)
img2 = cv2.rectangle(img,(x,y),(x+w,y+h),(0,255,0),2)

cv2.imshow('image',img2)

plt.imshow(img2),plt.show()
