import torch
import torch.nn as nn
import timeit as ti

# Custom binary step function
class stepfun(nn.Module):
    def __init__(self, threshold):
        super(stepfun, self).__init__()
        self.threshold = threshold

    def forward(self, x):
        return torch.where(x <= self.threshold, 0.0, 1.0)

# Custom neural net
class CustomNetXOR(nn.Module):
    def __init__(self):
        super(CustomNetXOR, self).__init__()
        self.layer1 = nn.Linear(2, 3)  # Input size: 2, output size: 3
        self.layer2 = nn.Linear(3, 2)  # Input size: 3, output size: 2
        self.layer3 = nn.Linear(2, 1)  # Input size: 2, output size: 1

        self.step_func = stepfun(threshold=-0.7)  # Use custom binary step function

    def forward(self, x):
        x = self.layer1(x)
        x = torch.cat((x[:, :1], self.step_func(x[:, 1:2]), x[:, 2:]), dim=1)  # Use identity function for first and third neurons, binary step function for the second
        x = self.layer2(x)
        x = self.step_func(x)
        x = self.layer3(x)
        x = self.step_func(x)
        return x


# Create an instance of the custom net
nn_XOR = CustomNetXOR()

# Set weights and deactivate biases
state_dict = nn_XOR.state_dict()

state_dict['layer1.weight'] = torch.tensor([[1.0, 0.0], [-0.5, -0.5], [0.0, 1.0]])
state_dict['layer1.bias'] = torch.tensor([0.0, 0.0, 0.0])

state_dict['layer2.weight'] = torch.tensor([[-0.5, -0.5, 0.0], [0.0, -0.5, -0.5]])
state_dict['layer2.bias'] = torch.tensor([0.0, 0.0])

state_dict['layer3.weight'] = torch.tensor([[-0.5, -0.5]])
state_dict['layer3.bias'] = torch.tensor([0.0])

nn_XOR.load_state_dict(state_dict)

# Create input tensors
new_input_0_0 = torch.tensor([[0.0, 0.0]])
new_input_0_1 = torch.tensor([[0.0, 1.0]])
new_input_1_0 = torch.tensor([[1.0, 0.0]])
new_input_1_1 = torch.tensor([[1.0, 1.0]])

# Make the predictions
prediction_XOR_0_0 = nn_XOR(new_input_0_0)
prediction_XOR_0_1 = nn_XOR(new_input_0_1)
prediction_XOR_1_0 = nn_XOR(new_input_1_0)
prediction_XOR_1_1 = nn_XOR(new_input_1_1)

# Measure average time needed for a prediction in nanoseconds
avg_time_XOR_0_0 = ti.timeit(lambda: nn_XOR(new_input_0_0), number=1000) / 1000 * 1e9
avg_time_XOR_0_1 = ti.timeit(lambda: nn_XOR(new_input_0_1), number=1000) / 1000 * 1e9
avg_time_XOR_1_0 = ti.timeit(lambda: nn_XOR(new_input_1_0), number=1000) / 1000 * 1e9
avg_time_XOR_1_1 = ti.timeit(lambda: nn_XOR(new_input_1_1), number=1000) / 1000 * 1e9

print("XOR Neural Net Prediction for Input 0,0 : ", prediction_XOR_0_0.item())
print(f"Average Time needed for one prediction (XOR: Input 0,0): {avg_time_XOR_0_0} ns\n")
print("XOR Neural Net Prediction for Input 0,1 : ", prediction_XOR_0_1.item())
print(f"Average Time needed for one prediction (XOR: Input 0,1): {avg_time_XOR_0_1} ns\n")
print("XOR Neural Net Prediction for Input 1,0 : ", prediction_XOR_1_0.item())
print(f"Average Time needed for one prediction (XOR: Input 1,0): {avg_time_XOR_1_0} ns\n")
print("XOR Neural Net Prediction for Input 1,1 : ", prediction_XOR_1_1.item())
print(f"Average Time needed for one prediction (XOR: Input 1,1): {avg_time_XOR_1_1} ns\n")