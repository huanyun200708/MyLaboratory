'''
Created on 2017/1/17

@author: hq
'''

class MemberCount(object):
    '''
    classdocs
    '''
    member = 0;
    members = [1,2,3];

    def __init__(self, params):
        '''
        Constructor
        '''
    def count(self):
        MemberCount.member += 1
        
    def countMember(self):
        MemberCount.members[0] += 1