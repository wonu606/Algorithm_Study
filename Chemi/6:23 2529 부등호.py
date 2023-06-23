a = int(input())
expression = list(input().split())
stack = []
max_ans = ''
min_ans = ''

def findMax(expression):
    for i in expression:
        while stack:
            if i == '>':
                stack.pop()
                max_answer += str(nums.pop(nums[max_idx]))
                max_answer += str(nums.pop(nums[max_idx+1]))
            
        if i == '>':
            max_answer += str(nums.pop())
        else:
            stack.append(i)
            max_idx -= 1
        
## 부등호 방향의 숫자
#  < >  stack
# 순서가 있다.
# 인덱스 활용
