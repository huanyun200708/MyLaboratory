'''
Created on 2016/11/17

@author: hq
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
            print("I'm %s %d years old" % (self.name, self.age))
        