package com.guicedee.website;

import com.jwebmp.core.base.angular.client.annotations.angular.NgDataType;
import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.structures.NgField;
import com.jwebmp.core.base.angular.client.annotations.structures.NgMethod;
import com.jwebmp.core.base.angular.client.annotations.structures.NgSignal;
import com.jwebmp.core.base.angular.client.services.interfaces.INgDataType;

/**
 * Angular injectable service that fetches the service status from the backend
 * and exposes signals for each site's health status.
 * <p>
 * Consumed by the header component to display status badges on product icons.
 */
@NgDataType(NgDataType.DataTypeClass.Class)
@NgImportReference(value = "Injectable", reference = "@angular/core")
@NgImportReference(value = "inject", reference = "@angular/core", onParent = true, onSelf = false)
@NgImportReference(value = "HttpClient", reference = "@angular/common/http")
@NgImportReference(value = "signal, computed", reference = "@angular/core")
@NgImportReference(value = "interval", reference = "rxjs")

@NgField(value = "statusService: StatusService = inject(StatusService);", onParent = true, onSelf = false)

@NgSignal(value = "null as any", type = "any", referenceName = "statusData")

@NgMethod("""
    private http = inject(HttpClient);

    fetchStatus() {
        this.http.get<any>('https://status.guicedee.com/api/status').subscribe({
            next: (data) => this.statusData.set(data),
            error: (err) => console.warn('Status fetch failed:', err)
        });
    }

    getServiceStatus(serviceName: string): string {
        const data = this.statusData();
        if (!data || !data.services) return 'UNKNOWN';
        const svc = data.services.find((s: any) => s.name === serviceName);
        return svc ? svc.status : 'UNKNOWN';
    }

    getStatusVariant(serviceName: string): string {
        const status = this.getServiceStatus(serviceName);
        switch (status) {
            case 'UP': return 'success';
            case 'DOWN': return 'danger';
            case 'DEGRADED': return 'warning';
            case 'WARNING': return 'warning';
            default: return 'neutral';
        }
    }

    getServiceHealthDetails(serviceName: string): any[] {
        const data = this.statusData();
        if (!data || !data.services) return [];
        const svc = data.services.find((s: any) => s.name === serviceName);
        if (!svc) return [];
        return svc.healthDetails || svc.checks || svc.details || [];
    }

    getHealthTooltip(serviceName: string): string {
        const status = this.getServiceStatus(serviceName);
        const details = this.getServiceHealthDetails(serviceName);
        
        let tooltip = serviceName + ': ' + status;
        if (details && details.length > 0) {
            details.forEach((d: any) => {
                tooltip += '\\n• ' + (d.name || d.check || d.key || 'check') + ': ' + (d.status || d.state || 'unknown');
            });
        }
        return tooltip;
    }
""")
public class StatusService implements INgDataType<StatusService>
{
    @Override
    public String renderBeforeClass()
    {
        return "@Injectable({ providedIn: 'root' })\n";
    }

    @Override
    public StringBuilder renderConstructorBody()
    {
        return new StringBuilder("""
                this.fetchStatus();
                interval(30000).subscribe(() => this.fetchStatus());
                """);
    }
}

