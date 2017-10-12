import numpy as np
import cv2
from matplotlib import pyplot as plt
img1 = cv2.imread('F:/codeFile/python/o.jpg') # queryImage
img2 = cv2.imread('F:/codeFile/python/search.png') # trainImage

height,width=img1.shape[:2]
img1=cv2.resize(img1,(3*width,3*height),interpolation=cv2.INTER_CUBIC)

height,width=img2.shape[:2]
img2=cv2.resize(img2,(3*width,3*height),interpolation=cv2.INTER_CUBIC)


# Initiate SIFT detector
orb = cv2.ORB_create()

fast = cv2.FastFeatureDetector_create(50)

kpf1 = fast.detect(img1,None)
kpff1, des1 = orb.compute(img1, kpf1)

kpf2 = fast.detect(img2,None)
kpff2, des2 = orb.compute(img2, kpf2)
# create BFMatcher object
bf = cv2.BFMatcher(cv2.NORM_L1, crossCheck=True)
# Match descriptors.
matches = bf.match(des1,des2)
# Sort them in the order of their distance.
matches = sorted(matches, key = lambda x:x.distance)

img3 = cv2.drawMatches(img1,kpff1,img2,kpff2,matches[:100],None,flags=2)  
plt.imshow(img3)  
plt.show()
