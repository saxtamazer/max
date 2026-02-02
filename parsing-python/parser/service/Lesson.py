# временный класс для хранения данных о паре
class Lesson:
    def __init__(self, group, day_of_week, time, data, even):
        self.group = group
        self.day_of_week = day_of_week
        self.time = time
        self.data = data
        self.even = even