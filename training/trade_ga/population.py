import random

from .individual import Individual
from .summary import Summary


class Population:
    def __init__(
            self, size: int, individual_size: int, min_value: int, max_value: int, elite_size=100, max_generation=5_000
    ):
        self.current_generation = 0
        self.max_generation = max_generation
        self.size = size
        self.individual_size = individual_size
        self.min_value = min_value
        self.max_value = max_value
        self.elite_size = elite_size
        self.individuals = []
        self.summary = Summary()
        self.hall_of_fame = []

    def initialize_population(self, fitness_function, historical_data):
        self.individuals = [Individual.create_random_individual(self.individual_size, self.min_value, self.max_value)
                            for _ in range(self.size)]
        self.evaluate_population(fitness_function, historical_data)

    def evaluate_population(self, fitness_function, historical_data):
        for individual in self.individuals:
            if individual.fitness is None:
                individual.fitness, *_ = fitness_function(individual.chromosome, historical_data)

    def select_parents(self, num_parents: int):
        sorted_individuals = sorted(self.individuals, key=lambda x: x.fitness, reverse=True)
        return sorted_individuals[:num_parents]

    def crossover(self, parents: list[Individual], crossover_point: int) -> list[Individual]:
        child1 = parents[0].copy()
        child2 = parents[1].copy()

        child1.chromosome[crossover_point:], child2.chromosome[crossover_point:] = \
            child2.chromosome[crossover_point:], child1.chromosome[crossover_point:]

        return [child1, child2]

    def mutate(self, individual: Individual, mutation_rate: float):
        prob_mutation = mutation_rate * (self.current_generation / self.max_generation)

        for i in range(len(individual.chromosome)):
            if random.random() < prob_mutation:
                individual.chromosome[i] = random.randint(self.min_value, self.max_value)

    def selecao_por_roleta(self):
        soma_aptidao = sum([i.fitness for i in self.individuals])

        probabilidades = [aptidao.fitness / soma_aptidao for aptidao in self.individuals]

        self.individuals = random.choices(self.individuals, k=self.size, weights=probabilidades)

    def selecao_por_torneio(self, tamanho_torneio: int = 5):
        nova_geracao = []
        for _ in range(self.size):
            torneio = random.sample(self.individuals, tamanho_torneio)
            vencedor = max(torneio, key=lambda i: i.fitness)
            nova_geracao.append(vencedor)
        self.individuals = nova_geracao

    def evolve(self, fitness_function, historical_data, num_parents: int, crossover_rate: float, mutation_rate: float):
        # parents = self.select_parents(num_parents)
        # self.selecao_por_roleta()
        self.selecao_por_torneio()
        children = []

        # prob_crossover = crossover_rate * ((self.max_generation - self.current_generation) / self.max_generation)
        # prob_crossover = max(crossover_rate*.8, prob_crossover)
        prob_crossover = crossover_rate
        for i in range(0, num_parents):
            if i + 1 < self.size and random.random() < prob_crossover:
                parents = [random.randint(0, self.size - 1) for i in range(2)]
                crossover_point = random.randint(0, self.individual_size - 1)
                offspring = self.crossover(
                    [self.individuals[parents[0]], self.individuals[parents[-1]]], crossover_point)
                children.extend(offspring)

        for child in children:
            self.mutate(child, mutation_rate)

        self.individuals = children
        self.evaluate_population(fitness_function, historical_data)
        self.update_hall_of_fame()
        self.update_summary()
        self.current_generation = min(self.current_generation + 1, self.max_generation)

    def update_summary(self):
        fitness_values = [individual.fitness for individual in self.individuals]
        self.summary.update(fitness_values)

    def get_summary(self) -> Summary:
        return self.summary

    def update_hall_of_fame(self):
        all_individuals = self.individuals + self.hall_of_fame
        all_individuals = sorted(all_individuals, key=lambda x: x.fitness, reverse=True)
        self.individuals = all_individuals[:self.size]
        self.hall_of_fame = all_individuals[:self.elite_size]
