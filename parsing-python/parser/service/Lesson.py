# временный класс для хранения данных о паре
class Lesson:
    def __init__(self, group, day_of_week, start_time, end_time, type, subject, teachers, rooms, even):
        self.group = group
        self.day_of_week = day_of_week
        self.start_time = start_time
        self.end_time = end_time
        self.type = type
        self.subject = subject
        self.teachers = teachers
        self.rooms = rooms
        self.even = even