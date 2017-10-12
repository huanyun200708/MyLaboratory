import numpy as np
import cv2
from matplotlib import pyplot as plt
img = cv2.imread('F:/codeFile/python/o.jpg',cv2.IMREAD_COLOR) # queryImage

height,width=img.shape[:2]
res=cv2.resize(img,(4*width,4*height),interpolation=cv2.INTER_CUBIC)

orb = cv2.ORB_create()
fast = cv2.FastFeatureDetector_create()

# find and draw the keypoints
kp1 = fast.detect(res,None)
kp, des = orb.compute(res, kp1)
img2 = cv2.drawKeypoints(res,kp,None,color=(0,255,0), flags=2)

cv2.imshow('image',img2)

plt.imshow(img2),plt.show()
