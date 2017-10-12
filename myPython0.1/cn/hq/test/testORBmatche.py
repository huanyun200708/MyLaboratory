import numpy as np
import cv2
from matplotlib import pyplot as plt
img1 = cv2.imread('F:/codeFile/python/o.jpg',0) # queryImage
img2 = cv2.imread('F:/codeFile/python/search.png',0) # trainImage

height,width=img1.shape[:2]
img1=cv2.resize(img1,(4*width,4*height),interpolation=cv2.INTER_CUBIC)

height,width=img2.shape[:2]
img2=cv2.resize(img2,(4*width,4*height),interpolation=cv2.INTER_CUBIC)
# Initiate SIFT detector
orb = cv2.ORB_create()
# find the keypoints and descriptors with SIFT
kp1, des1 = orb.detectAndCompute(img1,None)
kp2, des2 = orb.detectAndCompute(img2,None)
# create BFMatcher object
bf = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck=True)
# Match descriptors.
matches = bf.match(des1,des2)
# Sort them in the order of their distance.
matches = sorted(matches, key = lambda x:x.distance)
# Sort them in the order of their distance.
matches = sorted(matches, key = lambda x:x.distance)
img3 = cv2.drawMatches(img1,kp1,img2,kp2,matches[:10],None,flags=2)  
plt.imshow(img3)  
plt.show()  