import torch
import torch.nn as nn
import timeit as ti

# Custom binary step function
def stepfun(x, threshold):
    return torch.where(x <= threshold, 0.0, 1.0)

# Custom neural net
class CustomNet(nn.Module):
    def __init__(self, threshold):
        super(CustomNet, self).__init__()
        self.layer1 = nn.Linear(2,1)  # Input size: 2, output size: 3

        self.threshold = threshold

    def forward(self, x):
        x = self.layer1(x)
        x = stepfun(x, self.threshold)
        return x

# Create an instance of the custom net
nn_OR = CustomNet(threshold=0.5)

# Set weights and deactivate biases
nn_OR.layer1.weight = nn.Parameter(torch.tensor([[1.0, 1.0]]))
nn_OR.layer1.bias.data = torch.tensor([0.0])

# Create input tensors
new_input_0_0 = torch.tensor([[0.0, 0.0]])
new_input_0_1 = torch.tensor([[0.0, 1.0]])
new_input_1_0 = torch.tensor([[1.0, 0.0]])
new_input_1_1 = torch.tensor([[1.0, 1.0]])

# Make the predictions
prediction_OR_0_0 = nn_OR(new_input_0_0)
prediction_OR_0_1 = nn_OR(new_input_0_1)
prediction_OR_1_0 = nn_OR(new_input_1_0)
prediction_OR_1_1 = nn_OR(new_input_1_1)

# Measure average time needed for a prediction
avg_time_OR_0_0 = round(ti.timeit(lambda: nn_OR(new_input_0_0), number=1000) / 1000 * 1e9, 1)
avg_time_OR_0_1 = round(ti.timeit(lambda: nn_OR(new_input_0_1), number=1000) / 1000 * 1e9, 1)
avg_time_OR_1_0 = round(ti.timeit(lambda: nn_OR(new_input_1_0), number=1000) / 1000 * 1e9, 1)
avg_time_OR_1_1 = round(ti.timeit(lambda: nn_OR(new_input_1_1), number=1000) / 1000 * 1e9, 1)

print("OR Neural Net Prediction for Input 0,0 : ", prediction_OR_0_0.item())
print(f"Average Time needed for one prediction (OR: Input 0,0): {avg_time_OR_0_0} ns\n")
print("OR Neural Net Prediction for Input 0,1 : ", prediction_OR_0_1.item())
print(f"Average Time needed for one prediction (OR: Input 0,1): {avg_time_OR_0_1} ns\n")
print("OR Neural Net Prediction for Input 1,0 : ", prediction_OR_1_0.item())
print(f"Average Time needed for one prediction (OR: Input 1,0): {avg_time_OR_1_0} ns\n")
print("OR Neural Net Prediction for Input 1,1 : ", prediction_OR_1_1.item())
print(f"Average Time needed for one prediction (OR: Input 1,1): {avg_time_OR_1_1} ns\n")