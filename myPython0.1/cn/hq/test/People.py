'''
Created on 2016�?11�?17�?

@author: 黄淇
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
            print("我叫%s �?%d岁了" % (self.name, self.age))
        