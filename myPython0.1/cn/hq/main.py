'''
Created on 2016å¹?11æœ?18æ—?

@author: é»„æ·‡
'''
'''
def func(*name):
    print(type(name))
    print(name)
func(1,4,6)
func(5,6,7,1,2,3)

S = 'abcdefghijk'
for i in range(0,len(S),2):
    print(S[i])
'''    
# a new wrapper layer
def pre_str(pre=''):
    # old decorator
    def decorator(F):
        def new_F(a, b):
            print(pre + "input", a, b)
            return F(a, b)
        return new_F
    return decorator

# get square sum
@pre_str('^_^')
def square_sum(a, b):
    return a**2 + b**2

# get square diff
@pre_str('T_T')
def square_diff(a, b):
    return a**2 - b**2

print(square_sum(3, 4))
print(square_diff(3, 4))