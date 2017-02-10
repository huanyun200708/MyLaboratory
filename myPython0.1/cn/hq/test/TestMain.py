
from cn.hq.test.People import *
from cn.hq.classf.MemberCount import *

p = People('dd', 8)
p.introduceSelf()

m1 = MemberCount("")
m1.count()
#print(m1.member)

m2 = MemberCount("")
m2.count()
#print(m2.member)

m1.member = "ONE"
#print(m1.member)
#print(m2.member)

m1.countMember()
m2.countMember()

#print(m1.members)
#print(m2.members)
m1.members[0] = "TWO"
#print(m1.members)
#print(m2.members)
m1.m = "dfad"
print(m1.m)
