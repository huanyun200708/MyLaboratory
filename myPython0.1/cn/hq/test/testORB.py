import numpy as np
import cv2
from matplotlib import pyplot as plt
img = cv2.imread('F:/codeFile/python/search.png') # queryImage
# Initiate STAR detector
orb = cv2.ORB_create()
# find the keypoints with ORB
kp = orb.detect(img,None)
# compute the descriptors with ORB
kp, des = orb.compute(img, kp)
# draw only keypoints location,not size and orientation


fast = cv2.FastFeatureDetector_create(200)
# find and draw the keypoints
kp = fast.detect(img,None)
fast.detectAndCompute(img,None)
img2 = cv2.drawKeypoints(img,kp,None,color=(0,255,0), flags=2)
plt.imshow(img2),plt.show()

