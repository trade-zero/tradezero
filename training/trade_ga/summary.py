class Summary:
    def __init__(self):
        self.min_fitness = float('inf')
        self.max_fitness = float('-inf')
        self.mean_fitness = 0.0

    def update(self, fitness_values: list[float]):
        self.min_fitness = min(fitness_values)
        self.max_fitness = max(fitness_values)
        self.mean_fitness = sum(fitness_values) / len(fitness_values)
