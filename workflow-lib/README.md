# Fitness

## Fitness function-driven development

from: https://www.thoughtworks.com/insights/articles/fitness-function-driven-development

### Code quality

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

### Resiliency

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

### Observability

```
describe "Observability" do
	it "streams metrics" do
		expect(service.has_metrics()).to be(true)
	end
	it "has parseable logs" do
		expect(service.has_logs_in_aggregator()).to be(true)
	end
end
```

### Performance

```
describe "Performance" do
	it "completes a transaction under 10 seconds" do
		expect(transaction.check_transaction_round_trip_time()).to < 10
	end
	it "has less than 10% error rate for 10000 transactions" do
		expect(transaction.check_error_rate_for_transactions(10000)).to < .1
	end
end
```

### Compliance

```
describe "Compliance Standards" do
	describe "PII Compliance" do
		it "should not have PII in the logs" do
			expect(logs.has_pii_content()).to_not be(true)
		end
	end
	describe "GDPR Compliance" do
		it "should report types of personal information processed" do
			expect(gdpr.reports_PII_types()).to be(true)
		end
		it "should have been audited in the past year" do
			expect(gdpr.audit_age()).to < 365
		end
	end
end
```

### Security

```
describe "Security - Code Analysis" do
	describe “Code Analysis” do
		it "should use corporate-approved libraries only" do
			expect(code.only_uses_corporate_libraries()).to be(true)
		end
		it "should not have any of the OWASP Top 10" do
			expect(code.has_owasp_top_10()).to_not be(true)
		end
		it "should not have plaintext secrets in codebase" do
			expect(code.has_secrets_in_codebase()).to_not be(true)
		end
	end
	describe “CVE Analysis” do
		it "should not use libraries with known vulnerabilities" do
			expect(libraries.have_no_cves()).to be(true)
		end
		it "should not use a container image with known vulnerabilities" do
			expect(container.has_no_cves()).to be(true)
		end
	end
end
```

### Operability

```
describe "Operability Standards" do
	describe "Operations Check" do
		it "should have a service runbook" do
			expect(service.has_runbook()).to be(true)
		end
		it "should have a README" do
			expect(service.has_readme()).to be(true)
		end
		it "should have alerts" do
			expect(service.has_alerts()).to be(true)
		end
		it "should have tracing IDs" do
			expect(service.has_tracing_ids()).to be(true)
		end
	end
end
```
