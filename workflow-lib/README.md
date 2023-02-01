# Fitness

from: https://www.thoughtworks.com/insights/articles/fitness-function-driven-development

sample 1:

```cucumber
describe "Code Quality" do
    it "has test coverage above 90%" do
        expect(quality.get_test_coverage()).to > .9
    end
    it "has maintainability rating of .1 or higher (B)" do
        expect(quality.get_maintainability_rating()).to < .1
    end
end
```

sample 2:

```ruby
describe "Resiliency" do
	describe "New Deployment" do
		it "has less than 1% error rate for new deployment" do
			expect(new_deployment.get_error_rate()).to < .01
		end
	end
	describe "Network Latency" do
		it "has less than 5% error rate even if there is network latency" do
			expect(network_tests.get_error_rate()).to < .05
		end
		it "completes a transaction under 10 seconds even if there is network latency" do
			expect(network_tests.get_transaction_time()).to < 10
		end
	end
end
```
