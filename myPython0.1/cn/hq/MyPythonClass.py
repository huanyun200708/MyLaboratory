#!user/bin/python3
class MyClass:
    """"第一个python类"""
    i=123456
    def f(self):
        return 'Hello World'

#实例化对象
x = MyClass()

#调用类的属性和方法
print("MyClass类的属性i为:",x.i)
print("MyClass类的方法f输出为:",x.f())