import numpy as np
import cv2
from matplotlib import pyplot as plt
img = cv2.imread('F:/codeFile/python/search.png') # queryImage
orb = cv2.ORB_create()
fast = cv2.FastFeatureDetector_create(100)
# find and draw the keypoints
kp = fast.detect(img,None)
kp1, des = orb.compute(img, kp)
img2 = cv2.drawKeypoints(img,kp1,None,color=(0,255,0), flags=2)
cv2.imshow('image',img2)
plt.imshow(img2),plt.show()

