import random


class Individual:
    def __init__(self, chromosome: list[int] = None):
        self.chromosome = chromosome or []
        self.fitness = None

    @staticmethod
    def create_random_individual(size: int, min_value: int, max_value: int):
        chromosome = [random.randint(min_value, max_value) for _ in range(size)]
        return Individual(chromosome)

    def copy(self):
        return Individual(chromosome=self.chromosome.copy())
