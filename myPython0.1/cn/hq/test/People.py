'''
Created on 2016å¹?11æœ?17æ—?

@author: é»„æ·‡
'''

class People(object):
    '''
    classdocs
    '''
    name = ''
    age = 0

    def __init__(self, name, age):
        '''
        Constructor
        '''
        self.name = name
        self.age = age
    
    def introduceSelf(self):
            print("æˆ‘å«%s æˆ?%då²äº†" % (self.name, self.age))
        